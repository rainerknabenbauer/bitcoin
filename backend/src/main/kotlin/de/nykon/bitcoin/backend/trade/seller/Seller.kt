package de.nykon.bitcoin.backend.trade.seller

import de.nykon.bitcoin.backend.trade.gatherer.repository.CompactSellOrderbookRepository
import de.nykon.bitcoin.backend.trade.gatherer.value.CompactSellOrderbook
import de.nykon.bitcoin.sdk.bitcoinDe.CreateOrder
import de.nykon.bitcoin.sdk.bitcoinDe.DeleteOrder
import de.nykon.bitcoin.sdk.bitcoinDe.ShowAccountInfo
import de.nykon.bitcoin.sdk.bitcoinDe.ShowMyOrders
import de.nykon.bitcoin.sdk.value.bitcoinde.Response
import de.nykon.bitcoin.sdk.value.bitcoinde.deleteOrder.OrderId
import de.nykon.bitcoin.sdk.value.bitcoinde.showMyOrders.ShowMyOrdersBody
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.math.BigDecimal

/**
 * Once activated, it creates a SELL offer for all available bitcoins.
 * It periodically fetches the current price and updates the offer.
 */
@Component
class Seller(
        private val config: SellerConfig,
        private val showAccountInfo: ShowAccountInfo,
        private val showMyOrders: ShowMyOrders,
        private val deleteOrder: DeleteOrder,
        private val createOrder: CreateOrder,
        private val compactSellOrderbookRepository: CompactSellOrderbookRepository
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)


    /**
     * Triggers sale once target price is reached.
     */
    @Scheduled(fixedDelay = 15000)
    fun sellOnceTargetPriceIsReached() {
        if (inactiveSeller()) {
            val currentSellOrders = compactSellOrderbookRepository.findFirstByOrderByDateTimeDesc()

            if (targetPriceReached(currentSellOrders)) {
                log.info("Automatic activation of Seller! Weighted average " +
                        "${currentSellOrders.weightedAverage} is larger than target price ${config.targetPrice}")
                config.isActive = true
            } else {
                log.info("Weighted average ${currentSellOrders.weightedAverage} is smaller " +
                        "than target price ${config.targetPrice}")
            }
        }
    }

    private fun inactiveSeller() = !config.isActive
    private fun targetPriceReached(currentSellOrder: CompactSellOrderbook) =
            currentSellOrder.weightedAverage.compareTo(config.targetPrice) == 1

    /**
     * Sells all coins until empty.
     */
    @Scheduled(fixedDelay = 15000)
    fun sellCoins() {

        if (config.isActive) {

            log.info("Seller schedule is active.")

            val currentSells =
                    compactSellOrderbookRepository.findFirstByOrderByDateTimeDesc()

            /* Delete outdated bids and re-submit with new average price */

            log.info("Updating SELLER to ${currentSells.weightedAverage}")

            val myOrders = showMyOrders.all()
            if (config.isLiveChange) deleteOrders(myOrders)

            val accountInfo = showAccountInfo.execute()
            val availableCoins = accountInfo.body.data.balances.btc.available_amount

            createOrder(availableCoins, currentSells.weightedAverage, myOrders.body.credits)
        }
    }

    fun createOrder(availableCoins: BigDecimal, averagePrice: BigDecimal, credits: Int) {
        if (availableCoins == BigDecimal.ZERO) {
            config.isActive = false
        } else {
            if (config.isLiveChange) createOrder.sell(averagePrice, availableCoins)
            else log.info("Average price: $averagePrice | available coins: $availableCoins | credits: $credits")
        }
    }

    /* Remove all active orders */
    private fun deleteOrders(myOrders: Response<ShowMyOrdersBody>) {
        if (myOrders.body.orders != null) {
            myOrders.body.orders!!
                    .forEach { order -> deleteOrder.execute(OrderId(order.order_id)) }
        }
    }

}