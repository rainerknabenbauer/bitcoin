package de.nykon.bitcoin.backend.gatherer.value

import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document(collection = "publicTradeHistory")
data class LongTermTrade(
        val amount_currency_to_trade: BigDecimal,
        val date: String,
        val price: BigDecimal,
        val tid: Int
)