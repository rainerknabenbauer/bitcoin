package de.nykon.bitcoin.backend.trading.schedules

import de.nykon.bitcoin.backend.trading.schedules.config.SellSchedulerConfiguration
import de.nykon.bitcoin.sdk.bitcoinDe.*
import de.nykon.bitcoin.sdk.value.Response
import de.nykon.bitcoin.sdk.value.TransactionType
import de.nykon.bitcoin.sdk.value.showMyOrders.ShowMyOrdersBody
import de.nykon.bitcoin.sdk.value.showOrderbook.ShowOrderbookBody
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode

@Component
class Seller(private val config: SellSchedulerConfiguration) {

    private val apiKey = System.getenv("bitcoin.api.key")!!
    private val apiSecret = System.getenv("bitcoin.api.secret")!!

    private val consideredOrderSize = 5

    private val showAccountInfo = ShowAccountInfo(apiKey, apiSecret)
    private val showMyOrders = ShowMyOrders(apiKey, apiSecret)
    private val showOrderbook = ShowOrderbook(apiKey, apiSecret)
    private val deleteOrder = DeleteOrder(apiKey, apiSecret)
    private val createOrder = CreateOrder(apiKey, apiSecret)

    @Scheduled(cron = "0 * * * * *")
    fun sellCoins() {

        if (config.isActive) {

            val myOrders = showMyOrders.all()
            val sellOrderbook = showOrderbook.sell()

            val myLowestPrice = findMyLowestPrice(myOrders)
            val averagePrice = findAveragePrice(sellOrderbook)

            if (averagePrice < myLowestPrice) {
                optimizeSalesRank(myOrders, averagePrice)
            }
        }
    }

    /* Remove all active orders */
    private fun deleteOrders(myOrders: Response<ShowMyOrdersBody>) {
        myOrders.body.orders
                .map { order -> order.order_id }
                .forEach(deleteOrder::execute)
    }

    /* Get the cheapest offers and calculate an average price */
    private fun findAveragePrice(sellOrderbook: Response<ShowOrderbookBody>): BigDecimal {
        return sellOrderbook.body.orders
                .filterIndexed { index, _ -> index < consideredOrderSize }
                .map { order -> order.price }
                .reduce(BigDecimal::add)
                .divide(consideredOrderSize.toBigDecimal(), RoundingMode.DOWN).setScale(2)
    }

    /* Get my lowest price. If no price is available, default to zero */
    private fun findMyLowestPrice(myOrders: Response<ShowMyOrdersBody>): BigDecimal {
        return myOrders.body.orders
                .filter { it.type == TransactionType.SELL.name }
                .map { order -> order.price }
                .min()
                ?: BigDecimal.ZERO
    }

    /* Delete outdated bids and re-submit with new average price */
    private fun optimizeSalesRank(myOrders: Response<ShowMyOrdersBody>, averagePrice: BigDecimal) {
        deleteOrders(myOrders)
        val accountInfo = showAccountInfo.execute()
        val availableCoins = accountInfo.body.data.balances.btc.available_amount.setScale(8)

        if (availableCoins == BigDecimal.ZERO) {
            deactivateSchedule()
        } else {
            createOrder.sell(averagePrice, availableCoins)
        }
    }

    private fun deactivateSchedule() {
        config.isActive = false
    }

}