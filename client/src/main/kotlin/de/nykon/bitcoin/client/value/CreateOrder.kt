package de.nykon.bitcoin.client.value

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

data class CreateOrder(
        val httpMethod: String = "POST",
        val basepath: String = "https://api.bitcoin.de/v4/btceur/orders",
        val requestParams: String =
                "type=sell" +
                "&payment_option=1" +
                "&amount_currency_to_trade=AVAILABLE_AMOUNT",
        val uriFull: String = "$basepath?$requestParams",
        val nonce: String = System.currentTimeMillis().toString()
) {

    fun buildRequest() {

    }





    fun requestBody(): RequestBody {
        return rawJson().toRequestBody("application/json".toMediaType())
    }

    private fun rawJson(): String {
        val filename = "json/executeTrade_SELL.json"
        return File(filename).readText(Charsets.UTF_8)
    }

    fun json(rawJson: String): String {



        return rawJson.replace("MAX_COINS", "12")
    }

}