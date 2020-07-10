package de.nykon.bitcoin.sdk.value.bitcoinde.showOrderbookCompact

import java.math.BigDecimal

data class Ask(
        val amount_currency_to_trade: BigDecimal,
        val price: BigDecimal
)