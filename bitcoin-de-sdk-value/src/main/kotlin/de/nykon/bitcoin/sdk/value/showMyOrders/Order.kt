package de.nykon.bitcoin.sdk.value.showMyOrders

data class Order(
    val created_at: String,
    val end_datetime: String,
    val is_external_wallet_order: Boolean,
    val max_amount_currency_to_trade: String,
    val max_volume_currency_to_pay: Double,
    val min_amount_currency_to_trade: String,
    val min_volume_currency_to_pay: Double,
    val new_order_for_remaining_amount: Boolean,
    val order_id: String,
    val order_requirements: OrderRequirements,
    val price: Double,
    val state: Int,
    val trading_pair: String,
    val type: String
)