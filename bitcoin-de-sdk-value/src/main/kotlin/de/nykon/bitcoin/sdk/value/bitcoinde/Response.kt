package de.nykon.bitcoin.sdk.value.bitcoinde

data class Response<T>(
        val statusCode: Int,
        val body: T
)