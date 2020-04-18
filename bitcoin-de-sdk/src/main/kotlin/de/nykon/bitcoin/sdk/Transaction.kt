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

        /*
          Nonce is timestamped and part of the cryptographic signature.
          A nonce has the be unique and must be followed by increased in the next request.
          This is a fugly workaround until this gets independent of a timestamp in seconds.
         */
        Thread.sleep(1_100)

        return Response(receive.statusCode(), convert(receive.body()))
    }

}