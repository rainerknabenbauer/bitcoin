package de.nykon.bitcoin.sdk.value.cryptowatch.summary

data class Allowance(
    val cost: Int,
    val remaining: Long,
    val remainingPaid: Int,
    val upgrade: String
)