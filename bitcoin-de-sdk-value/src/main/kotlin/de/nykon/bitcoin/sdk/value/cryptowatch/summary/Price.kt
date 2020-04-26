package de.nykon.bitcoin.sdk.value.cryptowatch.summary

data class Price(
    val change: Change,
    val high: Double,
    val last: Double,
    val low: Int
)