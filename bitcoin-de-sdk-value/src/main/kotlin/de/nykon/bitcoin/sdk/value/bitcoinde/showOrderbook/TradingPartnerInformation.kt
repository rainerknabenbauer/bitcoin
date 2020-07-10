package de.nykon.bitcoin.sdk.value.bitcoinde.showOrderbook

data class TradingPartnerInformation(
    val amount_trades: Int,
    val bank_name: String,
    val bic: String,
    val is_kyc_full: Boolean,
    val rating: Int,
    val seat_of_bank: String,
    val trust_level: String,
    val username: String
)