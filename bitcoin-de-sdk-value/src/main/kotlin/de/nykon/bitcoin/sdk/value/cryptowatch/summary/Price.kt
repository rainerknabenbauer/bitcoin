package de.nykon.bitcoin.sdk.value.cryptowatch.summary

import java.math.BigDecimal

data class Price(
        val change: Change,
        val high: BigDecimal,
        val last: BigDecimal,
        val low: BigDecimal
)