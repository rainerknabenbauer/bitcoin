package de.nykon.bitcoin.client

import com.fasterxml.jackson.databind.ObjectMapper
import de.nykon.bitcoin.OrdersRoot
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class BitcoinDeClient {

    private var cryptoClient = CryptoClient()

    fun getOffers(): String? {

        val httpMethod = "GET"
        val uriString = "https://api.bitcoin.de/v4/btceur/orderbook"
        val requestParams = "type=buy"
        val requestBody = ""
        val uriFull = "$uriString?$requestParams"
        val apiKey = System.getenv("bitcoin.api.key")
        val apiSecret = System.getenv("bitcoin.api.secret")
        val nonce = System.currentTimeMillis().toString()
        val md5Hash = cryptoClient.hashMd5(requestBody)

        val hmacData = cryptoClient.getHmacData(httpMethod, uriFull, apiKey, nonce, md5Hash)
        val hmacSignature = cryptoClient.getHmacSignature(hmacData, apiSecret)
        val httpClient : HttpClient = HttpClient.newHttpClient()

        val httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(java.net.URI.create(uriFull))
                .setHeader("X-API-KEY", apiKey)
                .setHeader("X-API-NONCE", nonce)
                .setHeader("X-API-SIGNATURE", hmacSignature)
                .build()

        val receive = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())

        println(uriFull)
        println(receive.headers())
        println(receive.body())

        return receive.body()
    }

}