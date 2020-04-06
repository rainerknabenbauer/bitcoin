package de.nykon.bitcoin.sdk

interface DeleteOrder : Authentication {

    val orderId: String

    fun execute()
}