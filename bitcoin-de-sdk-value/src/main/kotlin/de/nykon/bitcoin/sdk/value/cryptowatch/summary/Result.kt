package de.nykon.bitcoin.sdk.value.cryptowatch.summary

data class Result(
    val price: Price,
    val volume: Double,
    val volumeQuote: Double
)