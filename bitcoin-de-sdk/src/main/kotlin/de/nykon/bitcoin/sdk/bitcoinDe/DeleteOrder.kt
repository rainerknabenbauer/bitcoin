package de.nykon.bitcoin.sdk.bitcoinDe

import com.google.gson.GsonBuilder
import de.nykon.bitcoin.sdk.value.Response
import de.nykon.bitcoin.sdk.value.deleteOrder.DeleteOrderBody
import de.nykon.bitcoin.sdk.value.deleteOrder.OrderId

open class DeleteOrder(
        override val apiKey: String,
        override val apiSecret: String)
    : Transaction<DeleteOrderBody>() {

    fun execute(orderId: OrderId): Response<DeleteOrderBody> {

        val json = this::class.java.getResource(jsonFile).readText(Charsets.UTF_8)
                .replace("{{api_key}}", apiKey)
                .replace("{{api_secret}}", apiSecret)
                .replace("{{order_id}}", orderId.value)

        return super.execute(json)
    }

    override fun convert(body: String?): DeleteOrderBody {
        return GsonBuilder().create().fromJson(body, DeleteOrderBody::class.java)
    }
}