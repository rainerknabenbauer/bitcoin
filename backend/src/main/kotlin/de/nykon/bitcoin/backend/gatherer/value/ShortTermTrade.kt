package de.nykon.bitcoin.backend.gatherer.value

import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

/**
 * Wraps the public trades within the last 24 hourss.
 */
@Document(collection = "rotatingPublicTradeHistory")
data class ShortTermTrade(
        val amount_currency_to_trade: BigDecimal,
        val date: String,
        val price: BigDecimal,
        val tid: Int
) {
}