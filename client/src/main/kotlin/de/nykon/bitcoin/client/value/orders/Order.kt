package de.nykon.bitcoin.client.value.orders

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.mongodb.core.mapping.Document

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
class Order {
    var order_id: String? = null
    var trading_pair: String? = null
    var is_external_wallet_order = false
    var type: String? = null
    var max_amount_currency_to_trade = 0.0
    var min_amount_currency_to_trade: String? = null
    var price = 0.0
    var max_volume_currency_to_pay = 0.0
    var min_volume_currency_to_pay = 0
    var orderRequirements: OrderRequirements? = null
    var tradingPartnerInformation: TradingPartnerInformation? = null
    var orderRequirements_fullfilled = false

}
