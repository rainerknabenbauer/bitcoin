package de.nykon.bitcoin.backend.trading.schedules

import de.nykon.bitcoin.backend.trading.schedules.config.BuyerSchedulConfig
import de.nykon.bitcoin.sdk.bitcoinDe.CreateOrder
import de.nykon.bitcoin.sdk.bitcoinDe.DeleteOrder
import de.nykon.bitcoin.sdk.bitcoinDe.ShowAccountInfo
import de.nykon.bitcoin.sdk.bitcoinDe.ShowMyOrders
import de.nykon.bitcoin.sdk.bitcoinDe.ShowOrderbook
import de.nykon.bitcoin.sdk.value.Response
import de.nykon.bitcoin.sdk.value.TransactionType
import de.nykon.bitcoin.sdk.value.showAccountInfo.FidorReservation
import de.nykon.bitcoin.sdk.value.showMyOrders.ShowMyOrdersBody
import de.nykon.bitcoin.sdk.value.showOrderbook.ShowOrderbookBody
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode

@Component
class Buyer(
        private val config: BuyerSchedulConfig,
        private val showAccountInfo: ShowAccountInfo,
        private val showMyOrders: ShowMyOrders,
        private val showOrderbook: ShowOrderbook,
        private val deleteOrder: DeleteOrder,
        private val createOrder: CreateOrder
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(fixedDelay = 8000)
    fun buyCoins() {

        if (config.isActive) {

            log.info("Buyer schedule is active.")

            val myOrders = showMyOrders.all()
            val buyOrderbook = showOrderbook.buy()

            val myLowestPrice = findMyLowestPrice(myOrders)
            val averagePrice = findAveragePrice(buyOrderbook)

            /* Delete outdated bids and re-submit with new average price */

            log.info("Updating BUYER from $myLowestPrice to $averagePrice")

            val accountInfo = showAccountInfo.execute()
            val fidorReservation = accountInfo.body.data.fidor_reservation

            if (fidorReservation != null) {
                val amountOfCoins = calculateAmountOfCoins(fidorReservation, averagePrice)

                if (config.isLiveChange) deleteOrders(myOrders)

                setResult(amountOfCoins, averagePrice, buyOrderbook.body.credits)
            }
        }
    }

    fun calculateAmountOfCoins(fidorReservation: FidorReservation, averagePrice: BigDecimal): BigDecimal {
        return fidorReservation.available_amount.divide(averagePrice, 8, RoundingMode.DOWN)
    }

    fun setResult(availableCoins: BigDecimal, averagePrice: BigDecimal, credits: Int) {
        if (availableCoins == BigDecimal.ZERO) {
            deactivateSchedule()
        } else {
            if (config.isLiveChange) createOrder.sell(averagePrice, availableCoins)
            else log.info("Average price: $averagePrice | available coins: $availableCoins | credits: $credits")
        }
    }

    /* Remove all active orders */
    fun deleteOrders(myOrders: Response<ShowMyOrdersBody>) {
        if (myOrders.body.myOrders == null) {
            myOrders.body.myOrders!!
                    .map { order -> order.order_id }
                    .forEach { orderId -> deleteOrder.execute(orderId) }
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