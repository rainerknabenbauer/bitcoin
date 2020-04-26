package de.nykon.bitcoin.sdk.value.bitcoinde.executeTrade

import de.nykon.bitcoin.sdk.value.bitcoinde.Errors

/**
 * Contains the response body for a Execute Trade command and should be wrapped using a Response.
 *
 * @see de.nykon.bitcoin.sdk.value.Response
 */
class ExecuteTradeBody {
    var errors: List<Errors> = ArrayList()
    var credits = 0
}