package de.nykon.bitcoin.backend.trade.buyer

import de.nykon.bitcoin.sdk.bitcoinDe.CreateOrder
import de.nykon.bitcoin.sdk.bitcoinDe.DeleteOrder
import de.nykon.bitcoin.sdk.bitcoinDe.ShowAccountInfo
import de.nykon.bitcoin.sdk.bitcoinDe.ShowMyOrders
import de.nykon.bitcoin.sdk.bitcoinDe.ShowOrderbook
import de.nykon.bitcoin.sdk.value.bitcoinde.Response
import de.nykon.bitcoin.sdk.value.bitcoinde.deleteOrder.OrderId
import de.nykon.bitcoin.sdk.value.bitcoinde.showAccountInfo.FidorReservation
import de.nykon.bitcoin.sdk.value.bitcoinde.showMyOrders.ShowMyOrdersBody
import de.nykon.bitcoin.sdk.value.bitcoinde.showOrderbook.ShowOrderbookBody
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
        private val config: BuyerSchedulConfig,
        private val showAccountInfo: ShowAccountInfo,
        private val showMyOrders: ShowMyOrders,
        private val showOrderbook: ShowOrderbook,
        private val deleteOrder: DeleteOrder,
        private val createOrder: CreateOrder
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(fixedDelay = 15000)
    fun buyCoins() {

        if (config.isActive) {

            log.info("Buyer schedule is active. Live change is ${config.isLiveChange}")

            val myOrders = showMyOrders.all()
            val buyOrderbook = showOrderbook.buy()

            val averagePrice = findAveragePrice(buyOrderbook)

            /* Delete outdated bids and re-submit with new average price */

            log.info("Updating BUYER to $averagePrice")

            val accountInfo = showAccountInfo.execute()
            val fidorReservation = accountInfo.body.data.fidor_reservation

            if (fidorReservation != null) {
                val amountOfCoins = calculateAmountOfCoins(fidorReservation, averagePrice)

                if (config.isLiveChange) deleteOrders(myOrders)

                createOrder(amountOfCoins, averagePrice, buyOrderbook.body.credits)
            }
        }
    }

    fun calculateAmountOfCoins(fidorReservation: FidorReservation, averagePrice: BigDecimal): BigDecimal {
        return fidorReservation.available_amount.divide(averagePrice, 8, RoundingMode.DOWN)
    }

    private fun createOrder(availableCoins: BigDecimal, averagePrice: BigDecimal, credits: Int) {
        if (availableCoins == BigDecimal.ZERO) {
            deactivateSchedule()
        } else {
            if (config.isLiveChange) createOrder.buy(averagePrice, availableCoins)
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

    /* Get the cheapest offers and calculate an average price */
    private fun findAveragePrice(buyOrderbook: Response<ShowOrderbookBody>): BigDecimal {
        return buyOrderbook.body.orders
                .subList(0, config.consideredOrderSize)
                .map { order -> order.price }
                .reduce(BigDecimal::add)
                .divide(config.consideredOrderSize.toBigDecimal(), 2, RoundingMode.HALF_DOWN)
    }

    private fun deactivateSchedule() {
        config.isActive = false
    }


}