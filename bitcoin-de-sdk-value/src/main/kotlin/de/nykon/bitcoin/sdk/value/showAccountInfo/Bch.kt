package de.nykon.bitcoin.sdk.value.showAccountInfo

import java.math.BigDecimal

data class Bch(
    val available_amount: BigDecimal,
    val reserved_amount: BigDecimal,
    val total_amount: BigDecimal
)