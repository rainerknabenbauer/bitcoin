package de.nykon.bitcoin.client.repository.value

import de.nykon.bitcoin.client.value.orders.Order
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

/**
 * Saves all offers returned in one query.
 */
@Document(collection = "price")
data class PriceBatch(
        val timestamp: Long,
        val offer: List<Offer>
)