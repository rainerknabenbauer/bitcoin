package de.nykon.bitcoin.backend.repository.value

import de.nykon.bitcoin.client.repository.value.Offer
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Saves all offers returned in one query.
 */
@Document(collection = "price")
data class PriceBatch(
        val timestamp: Long,
        val offer: List<Offer>
)