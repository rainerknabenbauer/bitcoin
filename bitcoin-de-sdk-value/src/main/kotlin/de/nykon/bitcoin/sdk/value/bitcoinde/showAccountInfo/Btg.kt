package de.nykon.bitcoin.sdk.value.bitcoinde.showAccountInfo

import java.math.BigDecimal

data class Btg(
        val available_amount: BigDecimal,
        val reserved_amount: BigDecimal,
        val total_amount: BigDecimal
)