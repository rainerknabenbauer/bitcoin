package de.nykon.bitcoin.sdk.bitcoinDe

import com.google.gson.GsonBuilder
import de.nykon.bitcoin.sdk.value.bitcoinde.Response
import de.nykon.bitcoin.sdk.value.bitcoinde.TransactionType
import de.nykon.bitcoin.sdk.value.bitcoinde.deleteOrder.OrderId
import de.nykon.bitcoin.sdk.value.bitcoinde.executeTrade.ExecuteTradeBody
import java.math.BigDecimal

open class ExecuteTrade(
        override val apiKey: String,
        override val apiSecret: String)
    : Transaction<ExecuteTradeBody>() {

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