package de.nykon.bitcoin.sdk.bitcoinDe

import com.google.gson.GsonBuilder
import de.nykon.bitcoin.sdk.DeleteOrder
import de.nykon.bitcoin.sdk.bitcoinDe.value.OrderId
import de.nykon.bitcoin.sdk.value.DeleteOrderBody
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


data class DeleteOrder(
        override val apiKey: String,
        override val apiSecret: String)
    : DeleteOrder {

    private val uri = "https://nykon.de/bitcoin/deleteOrder.php"
    private val jsonFile = "/json/DeleteOrder.json"

    override fun execute(orderId: OrderId): Response<DeleteOrderBody> {

        val json = this::class.java.getResource(jsonFile).readText(Charsets.UTF_8)
                .replace("{{api_key}}", apiKey)
                .replace("{{api_secret}}", apiSecret)
                .replace("{{order_id}}", orderId.value)

        return execute(json)
    }

    override fun execute(json: String): Response<DeleteOrderBody> {

        val httpClient : HttpClient = HttpClient.newHttpClient()

        val httpRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(java.net.URI.create(uri))
                .build()

        val receive = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())

        return Response<DeleteOrderBody>(receive.statusCode(), convert(receive.body()))
    }

    private fun convert(body: String?): DeleteOrderBody {

        println(body)

        return GsonBuilder().create().fromJson(body, DeleteOrderBody::class.java)
    }
}