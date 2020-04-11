package de.nykon.bitcoin.backend.trading.schedules

import de.nykon.bitcoin.backend.trading.schedules.config.SellerSchedulConfig
import de.nykon.bitcoin.sdk.bitcoinDe.*
import de.nykon.bitcoin.sdk.value.Response
import de.nykon.bitcoin.sdk.value.TransactionType
import de.nykon.bitcoin.sdk.value.showMyOrders.ShowMyOrdersBody
import de.nykon.bitcoin.sdk.value.showOrderbook.ShowOrderbookBody
import org.jetbrains.kotlin.utils.addToStdlib.ifNotEmpty
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.math.BigDecimal

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

    @Scheduled(cron = "0 * * * * *")
    fun sellCoins() {

        if (config.isActive) {

            log.info("Seller schedule is active.")

            val myOrders = showMyOrders.all()
            val sellOrderbook = showOrderbook.sell()

            val myLowestPrice = findMyLowestPrice(myOrders)
            val averagePrice = findAveragePrice(sellOrderbook)

            if (myLowestPrice == BigDecimal.ZERO || averagePrice < myLowestPrice) {

                /* Delete outdated bids and re-submit with new average price */

                log.info("Updating offer from $myLowestPrice to $averagePrice")

                val accountInfo = showAccountInfo.execute()
                val availableCoins = accountInfo.body.data.balances.btc.available_amount

                if (config.isLiveChange) deleteOrders(myOrders)

                setResult(availableCoins, averagePrice)
            }
        }
    }

    fun setResult(availableCoins: BigDecimal, averagePrice: BigDecimal) {
        if (availableCoins == BigDecimal.ZERO) {
            deactivateSchedule()
        } else {
            if (config.isLiveChange) createOrder.sell(averagePrice, availableCoins)
        }
    }

    /* Remove all active orders */
    fun deleteOrders(myOrders: Response<ShowMyOrdersBody>) {
        if (myOrders.body.myOrders == null) {
            myOrders.body.myOrders!!
                    .map { order -> order.order_id }
                    .forEach{orderId -> deleteOrder.execute(orderId)}
        }
    }

    /* Get the cheapest offers and calculate an average price */
    fun findAveragePrice(sellOrderbook: Response<ShowOrderbookBody>): BigDecimal {
        return sellOrderbook.body.orders
                .filterIndexed { index, _ -> index < config.consideredOrderSize }
                .map { order -> order.price }
                .reduce(BigDecimal::add)
                .div(config.consideredOrderSize.toBigDecimal())
    }

    /* Get my lowest price. If no price is available, default to zero */
    fun findMyLowestPrice(myOrders: Response<ShowMyOrdersBody>): BigDecimal {
        return if (myOrders.body.myOrders == null) {
            BigDecimal.ZERO
        } else {
            myOrders.body.myOrders!!
                    .filter { it.type == TransactionType.SELL.name }
                    .map { order -> order.price }
                    .min()!!
        }
    }

    private fun deactivateSchedule() {
        config.isActive = false
    }

}