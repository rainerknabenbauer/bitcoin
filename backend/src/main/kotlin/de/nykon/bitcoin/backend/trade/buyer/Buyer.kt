package de.nykon.bitcoin.backend.trade.buyer

import de.nykon.bitcoin.backend.trade.gatherer.repository.CompactBuyOrderbookRepository
import de.nykon.bitcoin.backend.trade.gatherer.value.CompactSellOrderbook
import de.nykon.bitcoin.sdk.bitcoinDe.CreateOrder
import de.nykon.bitcoin.sdk.bitcoinDe.DeleteOrder
import de.nykon.bitcoin.sdk.bitcoinDe.ShowAccountInfo
import de.nykon.bitcoin.sdk.bitcoinDe.ShowMyOrders
import de.nykon.bitcoin.sdk.bitcoinDe.ShowOrderbook
import de.nykon.bitcoin.sdk.value.bitcoinde.Response
import de.nykon.bitcoin.sdk.value.bitcoinde.deleteOrder.OrderId
import de.nykon.bitcoin.sdk.value.bitcoinde.showAccountInfo.FidorReservation
import de.nykon.bitcoin.sdk.value.bitcoinde.showMyOrders.ShowMyOrdersBody
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Maintains all buy related trade activities.
 */
@Component
class Buyer(
        private val config: BuyerConfig,
        private val showAccountInfo: ShowAccountInfo,
        private val showMyOrders: ShowMyOrders,
        private val deleteOrder: DeleteOrder,
        private val createOrder: CreateOrder,
        private val showOrderbook: ShowOrderbook,
        private val compactBuyOrderbookRepository: CompactBuyOrderbookRepository
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Triggers sale once target price is reached.
     */
    @Scheduled(fixedDelay = 15000)
    fun buyOnceTargetPriceIsReached() {
        if (inactiveBuyer() && config.isAutomatized) {
            val currentBuyOrders = compactBuyOrderbookRepository.findFirstByOrderByDateTimeDesc()

            if (targetPriceReached(currentBuyOrders)) {
                log.info("Weighted average ${currentBuyOrders.weightedAverage} is larger " +
                        "than target price ${config.targetPrice}")
            } else {
                log.info("Automatic activation of Buyer! Weighted average " +
                        "${currentBuyOrders.weightedAverage} is smaller than target price ${config.targetPrice}")
                config.isActive = true
            }
        }
    }

    private fun inactiveBuyer() = !config.isActive
    private fun targetPriceReached(currentSellOrder: CompactSellOrderbook) =
            currentSellOrder.weightedAverage.compareTo(config.targetPrice) == 1

    /**
     * Buy coins until cash reservation is exhausted.
     */
    @Scheduled(fixedDelay = 15000)
    fun buyCoins() {

        if (config.isActive) {

            log.info("Buyer schedule is active. Live change is ${config.isLiveChange}")

            val currentBuys = compactBuyOrderbookRepository.findFirstByOrderByDateTimeDesc()

            /* Delete outdated bids and re-submit with new price */

            log.info("Average weighted buying price is at ${currentBuys.weightedAverage}")

            val accountInfo = showAccountInfo.execute()
            val fidorReservation = accountInfo.body.data.fidor_reservation

            if (fidorReservation != null) {
                val amountOfCoins = calculateAmountOfCoins(fidorReservation, currentBuys.weightedAverage)

                val myOrders = showMyOrders.all()
                if (config.isLiveChange) deleteOrders(myOrders)

                /* Set buying price */

                val showOrderbook = showOrderbook.buy()
                val rival = showOrderbook.body.orders
                        .first { order ->
                            order.max_amount_currency_to_trade > amountOfCoins
                                    || order.max_volume_currency_to_pay > config.minVolume }

                val adjustedPrice = rival.price.add(BigDecimal.ONE)
                        .setScale(2, RoundingMode.HALF_DOWN)

                createOrder(amountOfCoins, adjustedPrice, myOrders.body.credits)
            }
        }
    }

    fun calculateAmountOfCoins(fidorReservation: FidorReservation, averagePrice: BigDecimal): BigDecimal {
        return fidorReservation.available_amount.divide(averagePrice, 8, RoundingMode.DOWN)
    }

    private fun createOrder(availableCoins: BigDecimal, averagePrice: BigDecimal, credits: Int) {
        if (availableCoins == BigDecimal.ZERO) {
            config.isActive = false
        } else {
            if (config.isLiveChange) createOrder.buy(averagePrice, availableCoins)
            log.info("Adjusted price: $averagePrice | available budget: $availableCoins | credits: $credits")
        }
    }

    /* Remove all active orders */
    fun deleteOrders(myOrders: Response<ShowMyOrdersBody>) {
        if (myOrders.body.orders != null) {
            myOrders.body.orders!!
                    .forEach { order -> deleteOrder.execute(OrderId(order.order_id)) }
        }
    }
}