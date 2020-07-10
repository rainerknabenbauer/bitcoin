package de.nykon.bitcoin.sdk.cryptowatch

import com.google.gson.GsonBuilder
import de.nykon.bitcoin.sdk.value.bitcoinde.Response
import de.nykon.bitcoin.sdk.value.cryptowatch.summary.KrakenSummaryBody

class ShowKrakenSummary : Transaction<KrakenSummaryBody>() {

    val uri = "https://api.cryptowat.ch/markets/kraken/btceur/summary"

    fun execute(): Response<KrakenSummaryBody> {

        return super.execute(uri)
    }

    override fun convert(body: String?): KrakenSummaryBody {
        return GsonBuilder().create().fromJson(body, KrakenSummaryBody::class.java)
    }

}