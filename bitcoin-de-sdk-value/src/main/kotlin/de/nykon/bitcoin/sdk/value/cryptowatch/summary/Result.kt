package de.nykon.bitcoin.sdk.value.cryptowatch.summary

import java.math.BigDecimal

data class Result(
    val price: Price,
    val volume: BigDecimal,
    val volumeQuote: BigDecimal
)