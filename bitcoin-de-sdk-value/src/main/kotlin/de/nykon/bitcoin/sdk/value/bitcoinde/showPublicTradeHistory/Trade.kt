package de.nykon.bitcoin.sdk.value.bitcoinde.showPublicTradeHistory

import java.math.BigDecimal

open class Trade(
    val amount_currency_to_trade: BigDecimal,
    val date: String,
    val price: BigDecimal,
    val tid: Int
)