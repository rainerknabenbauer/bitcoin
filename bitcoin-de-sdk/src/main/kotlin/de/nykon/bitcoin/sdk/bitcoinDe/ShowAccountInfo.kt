package de.nykon.bitcoin.sdk.bitcoinDe

import com.google.gson.GsonBuilder
import de.nykon.bitcoin.sdk.Transaction
import de.nykon.bitcoin.sdk.value.Response
import de.nykon.bitcoin.sdk.value.showAccountInfo.ShowAccountInfoBody

data class ShowAccountInfo(
        override val apiKey: String,
        override val apiSecret: String)
    : Transaction<ShowAccountInfoBody>() {

    override val uri = "https://nykon.de/bitcoin/showAccountInfo.php"
    private val jsonFile = "/json/${this::class.simpleName}.json"

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