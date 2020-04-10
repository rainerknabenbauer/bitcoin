package de.nykon.bitcoin.sdk.value.executeTrade

import de.nykon.bitcoin.sdk.value.Errors

/**
 * Contains the response body for a Execute Trade command and should be wrapped using a Response.
 *
 * @see de.nykon.bitcoin.sdk.value.Response
 */
class ExecuteTradeBody {
    var errors: List<Errors>? = null
    var credits = 0
}