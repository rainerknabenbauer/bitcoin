package de.nykon.bitcoin.sdk.value.bitcoinde.showAccountInfo

data class ShowAccountInfoBody(
    val credits: Int,
    val `data`: Data,
    val errors: List<Any>
)