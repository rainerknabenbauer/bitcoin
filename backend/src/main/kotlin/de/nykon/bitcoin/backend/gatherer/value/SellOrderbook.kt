package de.nykon.bitcoin.backend.gatherer.value

import de.nykon.bitcoin.sdk.value.showOrderbook.Order
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "sellHistory")
data class SellOrderbook(
        val dateTime: LocalDateTime,
        val orders: List<Order>
)