package de.nykon.bitcoin.sdk.value.showAccountInfo

data class Btc(
    val available_amount: Double,
    val reserved_amount: Double,
    val total_amount: Double
)