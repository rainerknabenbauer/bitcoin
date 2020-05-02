package de.nykon.bitcoin.backend.trade.buyer

import de.nykon.bitcoin.backend.trade.gatherer.repository.CompactBuyOrderbookRepository
import de.nykon.bitcoin.backend.trade.gatherer.value.CompactSellOrderbook
import de.nykon.bitcoin.sdk.bitcoinDe.CreateOrder
import de.nykon.bitcoin.sdk.bitcoinDe.DeleteOrder
import de.nykon.bitcoin.sdk.bitcoinDe.ShowAccountInfo
import de.nykon.bitcoin.sdk.bitcoinDe.ShowMyOrders
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
 * Once activated, it creates a BUY offer for all available bitcoins.
 * It periodically fetches the current price and updates the offer.
 */
@Component
class Buyer(
        private val configBuyer: BuyerBuyerConfig,
        private val showAccountInfo: ShowAccountInfo,
        private val showMyOrders: ShowMyOrders,
        private val deleteOrder: DeleteOrder,
        private val createOrder: CreateOrder,
        private val compactBuyOrderbookRepository: CompactBuyOrderbookRepository
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Triggers sale once target price is reached.
     */
    @Scheduled(fixedDelay = 15000)
    fun buyOnceTargetPriceIsReached() {
        if (inactiveBuyer()) {
            val currentBuyOrders = compactBuyOrderbookRepository.findFirstByOrderByDateTimeDesc()

            if (targetPriceReached(currentBuyOrders)) {
                log.info("Weighted average ${currentBuyOrders.weightedAverage} is larger " +
                        "than target price ${configBuyer.targetPrice}")
            } else {
                log.info("Automatic activation of Buyer! Weighted average " +
                        "${currentBuyOrders.weightedAverage} is smaller than target price ${configBuyer.targetPrice}")
                configBuyer.isActive = true
            }
        }
    }

    private fun inactiveBuyer() = !configBuyer.isActive
    private fun targetPriceReached(currentSellOrder: CompactSellOrderbook) =
            currentSellOrder.weightedAverage.compareTo(configBuyer.targetPrice) == 1

    /**
     * Buy coins until cash reservation is exhausted.
     */
    @Scheduled(fixedDelay = 15000)
    fun buyCoins() {

        if (configBuyer.isActive) {

            log.info("Buyer schedule is active. Live change is ${configBuyer.isLiveChange}")

            val currentBuys = compactBuyOrderbookRepository.findFirstByOrderByDateTimeDesc()

            /* Delete outdated bids and re-submit with new average price */

            log.info("Updating BUYER to ${currentBuys.weightedAverage}")

            val accountInfo = showAccountInfo.execute()
            val fidorReservation = accountInfo.body.data.fidor_reservation

            if (fidorReservation != null) {
                val amountOfCoins = calculateAmountOfCoins(fidorReservation, currentBuys.weightedAverage)

                val myOrders = showMyOrders.all()
                if (configBuyer.isLiveChange) deleteOrders(myOrders)

                createOrder(amountOfCoins, currentBuys.weightedAverage, myOrders.body.credits)
            }
        }
    }

    fun calculateAmountOfCoins(fidorReservation: FidorReservation, averagePrice: BigDecimal): BigDecimal {
        return fidorReservation.available_amount.divide(averagePrice, 8, RoundingMode.DOWN)
    }

    private fun createOrder(availableCoins: BigDecimal, averagePrice: BigDecimal, credits: Int) {
        if (availableCoins == BigDecimal.ZERO) {
            configBuyer.isActive = false
        } else {
            if (configBuyer.isLiveChange) createOrder.buy(averagePrice, availableCoins)
            log.info("Average price: $averagePrice | available budget: $availableCoins | credits: $credits")
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