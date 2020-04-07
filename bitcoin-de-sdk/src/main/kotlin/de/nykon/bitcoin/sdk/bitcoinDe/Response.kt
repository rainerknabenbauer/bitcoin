package de.nykon.bitcoin.sdk.bitcoinDe

data class Response<T>(
        val statusCode: Int,
        val t: T
)