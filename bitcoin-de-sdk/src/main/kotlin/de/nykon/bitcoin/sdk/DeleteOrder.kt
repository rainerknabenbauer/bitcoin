package de.nykon.bitcoin.sdk

import de.nykon.bitcoin.sdk.value.Response
import de.nykon.bitcoin.sdk.value.deleteOrder.OrderId
import de.nykon.bitcoin.sdk.value.deleteOrder.DeleteOrderBody

interface DeleteOrder : Authentication {

    fun execute(orderId: OrderId): Response<DeleteOrderBody>
    fun execute(json: String): Response<DeleteOrderBody>
}