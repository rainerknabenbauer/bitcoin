package de.nykon.bitcoin.backend

import de.nykon.bitcoin.client.repository.value.Trade
import de.nykon.bitcoin.backend.repository.value.SupplyBatch
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

    fun process(ordersRoot: OrdersRoot): SupplyBatch? {

        val orders = ordersRoot.orders
        val offers = ArrayList<Trade>()
        var accumulatedPrices = BigDecimal.ZERO
        var averagePrice = BigDecimal.ZERO

        if (Objects.nonNull(orders)) {
            orders!!.forEach { order -> kotlin.run {

                val price = BigDecimal.valueOf(order.price);
                val amount = BigDecimal.valueOf(order.max_amount_currency_to_trade)

                offers.add(Trade(price, amount))

                accumulatedPrices = accumulatedPrices.add(price)
            } }

             averagePrice = accumulatedPrices.divideToIntegralValue(BigDecimal.valueOf(orders.size.toLong()))

        } else {
            log.error("Received 0 orders from bitcoin.de")
        }

        return SupplyBatch(System.currentTimeMillis(), 5, offers, averagePrice)
    }

}