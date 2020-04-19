package de.nykon.bitcoin.backend.trading.value

import de.nykon.bitcoin.sdk.value.showOrderbook.Order
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "buyOfferHistory")
data class BuyOrderbook(
        val dateTime: LocalDateTime,
        val orders: List<Order>
)