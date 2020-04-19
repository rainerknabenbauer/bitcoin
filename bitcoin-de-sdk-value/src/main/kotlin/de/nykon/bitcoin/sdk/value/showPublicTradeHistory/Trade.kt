package de.nykon.bitcoin.sdk.value.showPublicTradeHistory

import java.math.BigDecimal

data class Trade(
    val amount_currency_to_trade: BigDecimal,
    val date: String,
    val price: BigDecimal,
    val tid: Int
)