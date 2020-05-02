package de.nykon.bitcoin.backend.trade.gatherer.value

import java.math.BigDecimal

data class Trade(
    val date: String,
    val amount_currency_to_trade: BigDecimal,
    val price: BigDecimal,
    val tid: Int
)