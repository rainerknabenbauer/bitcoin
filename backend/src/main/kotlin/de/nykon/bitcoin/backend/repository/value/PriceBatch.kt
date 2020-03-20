package de.nykon.bitcoin.backend.repository.value

import de.nykon.bitcoin.client.repository.value.Offer
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Saves all offers returned in one query.
 */
@Document(collection = "price")
data class PriceBatch(
        val timestamp: Long,
        val cycleInMinutes: Int,
        val offer: List<Offer>,
        val average: BigDecimal
)