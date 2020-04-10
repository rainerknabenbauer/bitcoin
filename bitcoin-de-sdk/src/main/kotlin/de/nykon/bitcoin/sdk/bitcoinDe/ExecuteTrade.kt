package de.nykon.bitcoin.sdk.bitcoinDe

import com.google.gson.GsonBuilder
import de.nykon.bitcoin.sdk.Authentication
import de.nykon.bitcoin.sdk.Executable
import de.nykon.bitcoin.sdk.ExecuteTrade
import de.nykon.bitcoin.sdk.Transaction
import de.nykon.bitcoin.sdk.value.Response
import de.nykon.bitcoin.sdk.value.TransactionType
import de.nykon.bitcoin.sdk.value.deleteOrder.DeleteOrderBody
import de.nykon.bitcoin.sdk.value.deleteOrder.OrderId
import de.nykon.bitcoin.sdk.value.executeTrade.ExecuteTradeBody
import java.math.BigDecimal
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

data class ExecuteTrade(
        override val apiKey: String,
        override val apiSecret: String)
    : Transaction<ExecuteTradeBody>() {

    override val uri = "https://nykon.de/bitcoin/executeTrade.php"
    private val jsonFile = "/json/${this::class.simpleName}.json"

    /**
     * Conveniently wraps a SELL transaction in a natural language command
     * to make the framework more user friendly and less prone to error.
     */
    fun sell(orderId: OrderId, amount: BigDecimal): Response<ExecuteTradeBody> {

        return execute(TransactionType.SELL, orderId, amount)
    }

    /**
     * Conveniently wraps a BUY transaction in a natural language command
     * to make the framework more user friendly and less prone to error.
     */
    fun buy(orderId: OrderId, amount: BigDecimal): Response<ExecuteTradeBody> {

        return execute(TransactionType.BUY, orderId, amount)
    }


    fun execute(type: TransactionType, orderId: OrderId, amount: BigDecimal): Response<ExecuteTradeBody> {

        val json = this::class.java.getResource(jsonFile).readText(Charsets.UTF_8)
                .replace("{{api_key}}", apiKey)
                .replace("{{api_secret}}", apiSecret)
                .replace("{{type}}", type.name)
                .replace("{{order_id}}", orderId.value)
                .replace("{{amount}}", amount.toPlainString())

        return super.execute(json)
    }

    override fun convert(body: String?): ExecuteTradeBody {
        return GsonBuilder().create().fromJson(body, ExecuteTradeBody::class.java)
    }
}