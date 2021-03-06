package de.nykon.bitcoin.sdk.bitcoinDe

import com.google.gson.GsonBuilder
import de.nykon.bitcoin.sdk.value.bitcoinde.Response
import de.nykon.bitcoin.sdk.value.bitcoinde.TransactionType
import de.nykon.bitcoin.sdk.value.bitcoinde.showOrderbook.ShowOrderbookBody

open class ShowOrderbook (
        override val apiKey: String,
        override val apiSecret: String)
    : Transaction<ShowOrderbookBody>() {

    /**
     * Conveniently wraps the cheapest, public SELL (bids) offers in a natural language
     * command to make the framework more user friendly and less prone to error.
     */
    fun sell(): Response<ShowOrderbookBody> {

        return execute(TransactionType.BUY)
    }

    /**
     * Conveniently wraps the cheapest, public BUY (asks) offers in a natural language
     * command to make the framework more user friendly and less prone to error.
     */
    fun buy(): Response<ShowOrderbookBody> {

        return execute(TransactionType.SELL)
    }

    fun execute(type: TransactionType): Response<ShowOrderbookBody> {

        val json = this::class.java.getResource(jsonFile).readText(Charsets.UTF_8)
                .replace("{{api_key}}", apiKey)
                .replace("{{api_secret}}", apiSecret)
                .replace("{{type}}", type.name)

        return super.execute(json)
    }

    override fun convert(body: String?): ShowOrderbookBody {
        return GsonBuilder().create().fromJson(body, ShowOrderbookBody::class.java)
    }

}