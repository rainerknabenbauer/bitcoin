package de.nykon.bitcoin.backend.repository.value

import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

/**
 * Saves all offers returned in one query.
 */
@Document(collection = "supply")
data class SupplyBatch(
        val timestamp: Long,
        val cycleInMinutes: Int?,
        val trade: List<Trade>,
        val average: BigDecimal
)