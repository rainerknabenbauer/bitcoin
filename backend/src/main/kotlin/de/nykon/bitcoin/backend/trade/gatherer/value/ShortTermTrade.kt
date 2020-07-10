package de.nykon.bitcoin.backend.trade.gatherer.value

import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * Wraps the public trades within the last 24 hours.
 */
@Document(collection = "history.publictrade.rotating")
data class ShortTermTrade(
        val dateTime: LocalDateTime,
        val trades: List<Trade>
) {
}