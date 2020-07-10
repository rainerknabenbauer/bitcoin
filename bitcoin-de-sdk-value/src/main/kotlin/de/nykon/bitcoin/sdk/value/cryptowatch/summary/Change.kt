package de.nykon.bitcoin.sdk.value.cryptowatch.summary

import java.math.BigDecimal

data class Change(
        val absolute: BigDecimal,
        val percentage: BigDecimal
)