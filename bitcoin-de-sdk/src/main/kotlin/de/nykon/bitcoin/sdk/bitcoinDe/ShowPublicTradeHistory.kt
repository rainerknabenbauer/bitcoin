package de.nykon.bitcoin.sdk.bitcoinDe

import com.google.gson.GsonBuilder
import de.nykon.bitcoin.sdk.Transaction
import de.nykon.bitcoin.sdk.value.Response
import de.nykon.bitcoin.sdk.value.showPublicTradeHistory.ShowPublicTradeHistoryBody

class ShowPublicTradeHistory(
        override val apiKey: String,
        override val apiSecret: String)
    : Transaction<ShowPublicTradeHistoryBody>() {

    /**
     * Conveniently wraps all successful trades in the last 24 hours in a natural language
     * command to make the framework more user friendly and less prone to error.
     */
    fun all(): Response<ShowPublicTradeHistoryBody> {

        return execute()
    }

    fun execute(): Response<ShowPublicTradeHistoryBody> {

        val json = this::class.java.getResource(jsonFile).readText(Charsets.UTF_8)
                .replace("{{api_key}}", apiKey)
                .replace("{{api_secret}}", apiSecret)

        return super.execute(json)
    }

    override fun convert(body: String?): ShowPublicTradeHistoryBody {
        return GsonBuilder().create().fromJson(body, ShowPublicTradeHistoryBody::class.java)
    }

}