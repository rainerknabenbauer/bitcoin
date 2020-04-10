package de.nykon.bitcoin.sdk.value.showOrderbook

data class Order(
    val is_external_wallet_order: Boolean,
    val max_amount_currency_to_trade: String,
    val max_volume_currency_to_pay: Double,
    val min_amount_currency_to_trade: String,
    val min_volume_currency_to_pay: Double,
    val order_id: String,
    val order_requirements: OrderRequirements,
    val order_requirements_fullfilled: Boolean,
    val price: Double,
    val trading_pair: String,
    val trading_partner_information: TradingPartnerInformation,
    val type: String
)