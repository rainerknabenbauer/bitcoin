package de.nykon.bitcoin.sdk.value.bitcoinde.showMyTrades

import java.math.BigDecimal

data class Trade(
        val amount_currency_to_trade: String,
        val amount_currency_to_trade_after_fee: BigDecimal,
        val created_at: String,
        val fee_currency_to_pay: BigDecimal,
        val fee_currency_to_trade: BigDecimal,
        val is_external_wallet_trade: Boolean,
        val is_trade_marked_as_paid: Boolean,
        val my_rating_for_trading_partner: String,
        val payment_method: Int,
        val price: BigDecimal,
        val state: Int,
        val successfully_finished_at: String,
        val trade_id: String,
        val trading_pair: String,
        val type: String,
        val volume_currency_to_pay: BigDecimal,
        val volume_currency_to_pay_after_fee: BigDecimal
)