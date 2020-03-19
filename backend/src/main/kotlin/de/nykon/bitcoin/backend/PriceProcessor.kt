package de.nykon.bitcoin.backend

import de.nykon.bitcoin.OrdersRoot
import de.nykon.bitcoin.client.repository.value.Offer
import de.nykon.bitcoin.backend.repository.value.PriceBatch
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

        if (Objects.nonNull(orders)) {
            orders!!.forEach { order -> offers.add(
                    Offer(BigDecimal.valueOf(order.price),
                            BigDecimal.valueOf(order.max_amount_currency_to_trade))) }
        } else {
            log.error("Received 0 orders from bitcoin.de")
        }

        return PriceBatch(System.currentTimeMillis(), offers)
    }

}