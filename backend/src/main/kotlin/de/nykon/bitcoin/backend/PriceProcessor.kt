package de.nykon.bitcoin.backend

import de.nykon.bitcoin.client.repository.value.Offer
import de.nykon.bitcoin.backend.repository.value.PriceBatch
import de.nykon.bitcoin.backend.value.OrdersRoot
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

@Component
class PriceProcessor {

    private val log: Logger = LoggerFactory.getLogger(PriceProcessor::class.java)

    fun process(ordersRoot: OrdersRoot): PriceBatch? {

        val orders = ordersRoot.orders
        val offers = ArrayList<Offer>()
        var accumulatedPrices = BigDecimal.ZERO
        var averagePrice = BigDecimal.ZERO

        if (Objects.nonNull(orders)) {
            orders!!.forEach { order -> kotlin.run {

                val price = BigDecimal.valueOf(order.price);
                val amount = BigDecimal.valueOf(order.max_amount_currency_to_trade)

                offers.add(Offer(price, amount))

                accumulatedPrices = accumulatedPrices.add(price)
            } }

             averagePrice = accumulatedPrices.divideToIntegralValue(BigDecimal.valueOf(orders.size.toLong()))

        } else {
            log.error("Received 0 orders from bitcoin.de")
        }

        return PriceBatch(System.currentTimeMillis(), 5, offers, averagePrice)
    }

}