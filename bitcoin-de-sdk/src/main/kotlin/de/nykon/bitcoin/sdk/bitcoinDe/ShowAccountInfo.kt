package de.nykon.bitcoin.sdk.bitcoinDe

import com.google.gson.GsonBuilder
import de.nykon.bitcoin.sdk.value.Response
import de.nykon.bitcoin.sdk.value.showAccountInfo.ShowAccountInfoBody

open class ShowAccountInfo(
        override val apiKey: String,
        override val apiSecret: String)
    : Transaction<ShowAccountInfoBody>() {

    fun execute(): Response<ShowAccountInfoBody> {

        val json = this::class.java.getResource(jsonFile).readText(Charsets.UTF_8)
                .replace("{{api_key}}", apiKey)
                .replace("{{api_secret}}", apiSecret)

        return super.execute(json)
    }

    override fun convert(body: String?): ShowAccountInfoBody {
        return GsonBuilder().create().fromJson(body, ShowAccountInfoBody::class.java)
    }

}