package de.nykon.bitcoin.sdk.value.bitcoinde.showMyTrades

/**
 * Wraps all possible trade states according to SDK.
 *
 * @see TradingApiSdkV4::SHOW_MY_TRADES_PARAMETER_STATE
 */
enum class TradeState(val value: String) {
    CANCELLED("-1"),
    PENDING("0"),
    SUCCESSFUL("1")
}