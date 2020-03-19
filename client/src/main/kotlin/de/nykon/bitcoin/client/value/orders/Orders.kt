package de.nykon.bitcoin.client.value.orders

class Orders {
    var order_id: String? = null
    var trading_pair: String? = null
    var is_external_wallet_order = false
    var type: String? = null
    var max_amount_currency_to_trade: String? = null
    var min_amount_currency_to_trade: String? = null
    var price = 0
    var max_volume_currency_to_pay = 0.0
    var min_volume_currency_to_pay = 0
    var orderRequirements: OrderRequirements? = null
    var tradingPartnerInformation: TradingPartnerInformation? = null
    var orderRequirements_fullfilled = false

}
