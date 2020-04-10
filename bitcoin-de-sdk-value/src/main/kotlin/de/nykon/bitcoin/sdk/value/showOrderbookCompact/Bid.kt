package de.nykon.bitcoin.sdk.value.showOrderbookCompact

import java.math.BigDecimal

data class Bid(
        val amount_currency_to_trade: BigDecimal,
        val price: BigDecimal
)