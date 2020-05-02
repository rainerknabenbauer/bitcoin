package de.nykon.bitcoin.backend.trade.gatherer.value

import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

/**
 * Wraps the long term history of public trades.
 */
@Document(collection = "publicTradeHistory")
data class LongTermTrade(
        val amount_currency_to_trade: BigDecimal,
        val date: String,
        val price: BigDecimal,
        val tid: Int
)