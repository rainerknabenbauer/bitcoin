package de.nykon.bitcoin.sdk.bitcoinDe

import com.google.gson.GsonBuilder
import de.nykon.bitcoin.sdk.Transaction
import de.nykon.bitcoin.sdk.value.Response
import de.nykon.bitcoin.sdk.value.showOrderbookCompact.ShowOrderbookCompactBody

data class ShowOrderbookCompact(
        override val apiKey: String,
        override val apiSecret: String)
    : Transaction<ShowOrderbookCompactBody>() {

    /**
     * Conveniently wraps the cheapest, public SELL (bids) and BUY (asks) offers in a natural language
     * command to make the framework more user friendly and less prone to error.
     */
    fun all(): Response<ShowOrderbookCompactBody> {

        return execute()
    }

    fun execute(): Response<ShowOrderbookCompactBody> {

        val json = this::class.java.getResource(jsonFile).readText(Charsets.UTF_8)
                .replace("{{api_key}}", apiKey)
                .replace("{{api_secret}}", apiSecret)

        return super.execute(json)
    }

    override fun convert(body: String?): ShowOrderbookCompactBody {
        return GsonBuilder().create().fromJson(body, ShowOrderbookCompactBody::class.java)
    }

}