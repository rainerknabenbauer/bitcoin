package de.nykon.bitcoin.sdk.value.showMyOrdersRef

data class OrderRequirements(
    val min_trust_level: String,
    val only_kyc_full: Boolean,
    val payment_option: Int,
    val seat_of_bank: List<String>
)