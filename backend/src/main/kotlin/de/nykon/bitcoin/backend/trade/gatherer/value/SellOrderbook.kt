package de.nykon.bitcoin.backend.trade.gatherer.value

import de.nykon.bitcoin.sdk.value.bitcoinde.showOrderbook.Order
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "history.sell")
data class SellOrderbook(
        val dateTime: LocalDateTime,
        val orders: List<Order>
)