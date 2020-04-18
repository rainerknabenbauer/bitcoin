package de.nykon.bitcoin.sdk.value.showAccountInfo

import java.math.BigDecimal

data class FidorReservation(
        val available_amount: BigDecimal,
        val reserved_at: String,
        val total_amount: BigDecimal,
        val valid_until: String
)