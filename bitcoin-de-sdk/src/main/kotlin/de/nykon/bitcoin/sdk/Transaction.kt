package de.nykon.bitcoin.sdk

import de.nykon.bitcoin.sdk.value.Response
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

abstract class Transaction<T> : Authentication {

    val uri = "https://nykon.de/bitcoin/${this::class.simpleName}.php"
    val jsonFile = "/json/${this::class.simpleName}.json"

    abstract fun convert(body: String?): T

    fun execute(json: String): Response<T> {

        val httpClient : HttpClient = HttpClient.newHttpClient()

        val httpRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(java.net.URI.create(uri))
                .build()

        val receive = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())

        return Response(receive.statusCode(), convert(receive.body()))
    }

}