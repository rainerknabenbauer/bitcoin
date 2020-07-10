package de.nykon.bitcoin.backend.trade.gatherer.value

import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * Wraps the long term history of public trades.
 */
@Document(collection = "history.publictrade")
data class LongTermTrade(
        val dateTime: LocalDateTime,
        val trades: List<Trade>
)