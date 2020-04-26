package de.nykon.bitcoin.backend.trade.seller

import de.nykon.bitcoin.sdk.bitcoinDe.CreateOrder
import de.nykon.bitcoin.sdk.bitcoinDe.DeleteOrder
import de.nykon.bitcoin.sdk.bitcoinDe.ShowAccountInfo
import de.nykon.bitcoin.sdk.bitcoinDe.ShowMyOrders
import de.nykon.bitcoin.sdk.bitcoinDe.ShowOrderbook
import de.nykon.bitcoin.sdk.value.bitcoinde.Response
import de.nykon.bitcoin.sdk.value.bitcoinde.deleteOrder.OrderId
import de.nykon.bitcoin.sdk.value.bitcoinde.showMyOrders.ShowMyOrdersBody
import de.nykon.bitcoin.sdk.value.bitcoinde.showOrderbook.ShowOrderbookBody
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Once activated, it creates a SELL offer for all available bitcoins.
 * It periodically fetches the current price and updates the offer.
 */
@Component
class Seller(
        private val config: SellerSchedulConfig,
        private val showAccountInfo: ShowAccountInfo,
        private val showMyOrders: ShowMyOrders,
        private val showOrderbook: ShowOrderbook,
        private val deleteOrder: DeleteOrder,
        private val createOrder: CreateOrder
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(fixedDelay = 15000)
    fun sellCoins() {

        if (config.isActive) {

            log.info("Seller schedule is active.")

            val myOrders = showMyOrders.all()
            val sellOrderbook = showOrderbook.sell()

            val averagePrice = findAveragePrice(sellOrderbook)

            /* Delete outdated bids and re-submit with new average price */

            log.info("Updating SELLER to $averagePrice")

            val accountInfo = showAccountInfo.execute()
            val availableCoins = accountInfo.body.data.balances.btc.available_amount

            if (config.isLiveChange) deleteOrders(myOrders)

            createOrder(availableCoins, averagePrice, sellOrderbook.body.credits)
        }
    }

    fun createOrder(availableCoins: BigDecimal, averagePrice: BigDecimal, credits: Int) {
        if (availableCoins == BigDecimal.ZERO) {
            deactivateSchedule()
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

    /* Get the cheapest offers and calculate an average price */
    fun findAveragePrice(sellOrderbook: Response<ShowOrderbookBody>): BigDecimal {
        return sellOrderbook.body.orders
                .subList(0, config.consideredOrderSize)
                .map { order -> order.price }
                .reduce(BigDecimal::add)
                .divide(config.consideredOrderSize.toBigDecimal(), 2, RoundingMode.HALF_DOWN)
    }

    private fun deactivateSchedule() {
        config.isActive = false
    }

}