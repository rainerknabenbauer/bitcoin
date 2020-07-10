package de.nykon.bitcoin.sdk.bitcoinDe

import com.google.gson.GsonBuilder
import de.nykon.bitcoin.sdk.value.bitcoinde.Response
import de.nykon.bitcoin.sdk.value.bitcoinde.TransactionType
import de.nykon.bitcoin.sdk.value.bitcoinde.createOrder.CreateOrderBody
import java.math.BigDecimal

open class CreateOrder(
        override val apiKey: String,
        override val apiSecret: String)
    : Transaction<CreateOrderBody>() {

    private val tradeMinimum = BigDecimal.valueOf(60)

    /**
     * Conveniently wraps a SELL offer in a natural language command
     * to make the framework more user friendly and less prone to error.
     *
     * The minimal required amount for a trade on bitcoin.de is 60 € (amount*price)
     */
    fun sell(price: BigDecimal, amount: BigDecimal): Response<CreateOrderBody> {

        return conditional(TransactionType.SELL, price, amount)
    }

    /**
     * Conveniently wraps a BUY offer in a natural language command
     * to make the framework more user friendly and less prone to error.
     *
     * The minimal required amount for a trade on bitcoin.de is 60 € (amount*price)
     */
    fun buy(price: BigDecimal, amount: BigDecimal): Response<CreateOrderBody> {

        return conditional(TransactionType.BUY, price, amount)
    }

    private fun conditional(type: TransactionType, price: BigDecimal, amount: BigDecimal): Response<CreateOrderBody> {

        return if (tradeMinimumReached(price, amount)) {
            execute(type, price, amount)
        } else {
            Response(400, CreateOrderBody(0, listOf("Trade minimum ($tradeMinimum €) not reached"), ""))
        }
    }

    fun execute(type: TransactionType, price: BigDecimal, amount: BigDecimal): Response<CreateOrderBody> {

        val json = this::class.java.getResource(jsonFile).readText(Charsets.UTF_8)
                .replace("{{api_key}}", apiKey)
                .replace("{{api_secret}}", apiSecret)
                .replace("{{type}}", type.name)
                .replace("{{price}}", price.toPlainString())
                .replace("{{amount}}", amount.toPlainString())

        return super.execute(json)
    }

    override fun convert(body: String?): CreateOrderBody {
        return GsonBuilder().create().fromJson(body, CreateOrderBody::class.java)
    }

    private fun tradeMinimumReached(price: BigDecimal, amount: BigDecimal): Boolean {
        return price.multiply(amount) >= tradeMinimum
    }
}