package de.nykon.bitcoin.sdk.value.showOrderbook

import java.math.BigDecimal

data class Order(
        val is_external_wallet_order: Boolean,
        val max_amount_currency_to_trade: String,
        val max_volume_currency_to_pay: BigDecimal,
        val min_amount_currency_to_trade: String,
        val min_volume_currency_to_pay: BigDecimal,
        val order_id: String,
        val order_requirements: OrderRequirements,
        val order_requirements_fullfilled: Boolean,
        val price: BigDecimal,
        val trading_pair: String,
        val trading_partner_information: TradingPartnerInformation,
        val type: String
)