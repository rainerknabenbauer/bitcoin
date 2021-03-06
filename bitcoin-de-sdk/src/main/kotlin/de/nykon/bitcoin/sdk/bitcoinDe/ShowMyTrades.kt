package de.nykon.bitcoin.sdk.bitcoinDe

 import com.google.gson.GsonBuilder
 import de.nykon.bitcoin.sdk.value.bitcoinde.Response
 import de.nykon.bitcoin.sdk.value.bitcoinde.showMyTrades.ShowMyTradesBody
 import de.nykon.bitcoin.sdk.value.bitcoinde.showMyTrades.TradeState
 import java.time.LocalDateTime
 import java.time.temporal.ChronoUnit

open class ShowMyTrades(
        override val apiKey: String,
        override val apiSecret: String)
    : Transaction<ShowMyTradesBody>() {

    fun execute(startDate: LocalDateTime, state: TradeState): Response<ShowMyTradesBody> {

        val millisRemoved = startDate.minusDays(1).truncatedTo(ChronoUnit.SECONDS)
        return execute(millisRemoved.toString(), state)
    }

    /**
     * Get all trades of your account.
     */
    fun execute(startDate: String, state: TradeState): Response<ShowMyTradesBody> {

        val json = this::class.java.getResource(jsonFile).readText(Charsets.UTF_8)
                .replace("{{api_key}}", apiKey)
                .replace("{{api_secret}}", apiSecret)
                .replace("{{start}}", "$startDate+02:00")
                .replace("{{state}}", state.value)

        return super.execute(json)
    }

    override fun convert(body: String?): ShowMyTradesBody {
        return GsonBuilder().create().fromJson(body, ShowMyTradesBody::class.java)
    }

}