package de.nykon.bitcoin.sdk

import de.nykon.bitcoin.sdk.bitcoinDe.Response
import de.nykon.bitcoin.sdk.bitcoinDe.value.OrderId
import de.nykon.bitcoin.sdk.value.DeleteOrderBody

interface DeleteOrder : Authentication {

    fun execute(orderId: OrderId): Response<DeleteOrderBody>
    fun execute(json: String): Response<DeleteOrderBody>
}