package de.nykon.bitcoin.sdk.bitcoinDe

import com.google.gson.GsonBuilder
import de.nykon.bitcoin.sdk.value.Response
import de.nykon.bitcoin.sdk.value.showMyOrders.ShowMyOrdersBody

open class ShowMyOrders(
        override val apiKey: String,
        override val apiSecret: String)
    : Transaction<ShowMyOrdersBody>() {

    /**
     * Conveniently lists all active orders of this account in a natural language
     * command to make the framework more user friendly and less prone to error.
     */
    fun all(): Response<ShowMyOrdersBody> {

        return execute()
    }

    fun execute(): Response<ShowMyOrdersBody> {

        val json = this::class.java.getResource(jsonFile).readText(Charsets.UTF_8)
                .replace("{{api_key}}", apiKey)
                .replace("{{api_secret}}", apiSecret)

        return super.execute(json)
    }

    override fun convert(body: String?): ShowMyOrdersBody {
        return GsonBuilder().create().fromJson(body, ShowMyOrdersBody::class.java)
    }

}