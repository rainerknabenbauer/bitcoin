package de.nykon.bitcoin.backend.trade.gatherer.value

import de.nykon.bitcoin.sdk.value.cryptowatch.summary.Change
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

/**
 * Wraps the summary of Kraken.com.
 */
@Document(collection = "summary.kraken")
data class KrakenSummary (
        val high: BigDecimal,
        val last: BigDecimal,
        val low: BigDecimal,
        val change: Change,
        val volume: BigDecimal
)