package de.nykon.bitcoin.sdk.value

data class Response<T>(
        val statusCode: Int,
        val t: T
)