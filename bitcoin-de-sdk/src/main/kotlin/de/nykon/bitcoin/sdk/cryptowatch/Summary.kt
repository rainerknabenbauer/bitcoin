package de.nykon.bitcoin.sdk.cryptowatch

import com.google.gson.GsonBuilder
import de.nykon.bitcoin.sdk.value.cryptowatch.summary.SummaryBody

class Summary : Transaction<SummaryBody>() {

    val uri = "https://api.cryptowat.ch/markets/kraken/btceur/summary"

    override fun convert(body: String?): SummaryBody {
        return GsonBuilder().create().fromJson(body, SummaryBody::class.java)
    }

}