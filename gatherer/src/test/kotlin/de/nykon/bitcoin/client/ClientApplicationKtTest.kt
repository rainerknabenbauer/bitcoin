package de.nykon.bitcoin.client

import com.fasterxml.jackson.databind.ObjectMapper
import de.nykon.bitcoin.client.value.OrdersRoot
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.jetbrains.kotlin.resolve.resolveQualifierAsReceiverInExpression
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Paths
import java.util.*


@SpringBootTest(classes = [ClientApplication::class])
internal class ClientApplicationKtTest {

    var cryptoClient = CryptoClient()

    fun `GET btceur order book on bitcoin_de`() {

        val httpMethod = "GET"
        val basepath = "https://api.bitcoin.de/v4/btceur/orderbook"
        val requestParams = "type=buy"
        val requestBody = ""
        val uriFull = "$basepath?$requestParams"
        val apiKey = System.getenv("bitcoin.api.key")
        val apiSecret = System.getenv("bitcoin.api.secret")
        val nonce = System.currentTimeMillis().toString()
        val md5Hash = cryptoClient.hashMd5(requestBody)

        val hmacData = cryptoClient.getHmacData(httpMethod, uriFull, apiKey, nonce, md5Hash)
        val hmacSignature = cryptoClient.getHmacSignature(hmacData, apiSecret)
        val httpClient : HttpClient = HttpClient.newHttpClient()

        val httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uriFull))
                .setHeader("X-API-KEY", apiKey)
                .setHeader("X-API-NONCE", nonce)
                .setHeader("X-API-SIGNATURE", hmacSignature)
                .build()

        val receive = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())

        println(uriFull)
        println(receive.headers())
        println(receive.body())
    }

    fun `GET orders on bitcoin_de`() {

        val httpMethod = "GET"
        val basepath = "https://api.bitcoin.de/v4/orders"
        val requestParams = ""
        val requestBody = ""
        val uriFull = "$basepath"
        val apiKey = System.getenv("bitcoin.api.key")
        val apiSecret = System.getenv("bitcoin.api.secret")
        val nonce = System.currentTimeMillis().toString()
        val md5Hash = cryptoClient.hashMd5(requestBody)

        val hmachData = cryptoClient.getHmacData(httpMethod, basepath, apiKey, nonce, md5Hash)
        val hmacSignature = cryptoClient.getHmacSignature(hmachData, apiSecret)
        val httpClient : HttpClient = HttpClient.newHttpClient()

        val httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uriFull))
                .setHeader("X-API-KEY", apiKey)
                .setHeader("X-API-NONCE", nonce)
                .setHeader("X-API-SIGNATURE", hmacSignature)
                .build()

        val send = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())

        val objectMapper = ObjectMapper()
        val readValue = objectMapper.readValue(send.body().toByteArray(), OrdersRoot::class.java)

        println(uriFull)
        println(send.headers())
        println(send.body())

        if (Objects.nonNull(readValue.orders)) {
            println("Count: " + readValue.orders!!.count())
        }

    }

    @Test
    fun `md5 hashing of request param returns correct checksum on empty string`() {

        val urlEncodedQuery = ""
        val expectedHash = "d41d8cd98f00b204e9800998ecf8427e"

        val actualHash = cryptoClient.hashMd5(urlEncodedQuery)

        Assertions.assertEquals(expectedHash, actualHash)
    }

    @Test
    fun `md5 hashing of request param returns correct checksum`() {

        val urlEncodedQuery = "max_amount=5.3&price=255.5&type=buy"
        val expectedHash = "5f4aece1d75c7adfc5ef346216e9bb11"

        val actualHash = cryptoClient.hashMd5(urlEncodedQuery)

        Assertions.assertEquals(expectedHash, actualHash)
    }

    @Test
    fun `concat of request params returns valid string`() {

        val httpMethod = "POST"
        val uri = "https://api.bitcoin.de/v4/orders"
        val apiKey = "MY_API_KEY"
        val nonce = "1234567"
        val md5Hash = "5f4aece1d75c7adfc5ef346216e9bb11"

        val expected = "POST#https://api.bitcoin.de/v4/orders#MY_API_KEY#1234567#5f4aece1d75c7adfc5ef346216e9bb11"

        val actual = cryptoClient.getHmacData(httpMethod, uri, apiKey, nonce, md5Hash)

        Assertions.assertEquals(actual, expected)
    }

}