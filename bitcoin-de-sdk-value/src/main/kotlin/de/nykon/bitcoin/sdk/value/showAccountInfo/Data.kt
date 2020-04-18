package de.nykon.bitcoin.sdk.value.showAccountInfo

data class Data(
    val balances: Balances,
    val encrypted_information: EncryptedInformation,
    val fidor_reservation: FidorReservation?
)