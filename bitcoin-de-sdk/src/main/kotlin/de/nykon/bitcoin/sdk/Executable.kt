package de.nykon.bitcoin.sdk

import de.nykon.bitcoin.sdk.value.Response
import de.nykon.bitcoin.sdk.value.deleteOrder.OrderId
import de.nykon.bitcoin.sdk.value.deleteOrder.DeleteOrderBody

interface Executable<T> {

    fun execute(json: String): Response<T>
}