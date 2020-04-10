package de.nykon.bitcoin.sdk.value.deleteOrder

import de.nykon.bitcoin.sdk.value.Errors

/**
 * Contains the response body for a Delete Order command and should be wrapped using a Response.
 *
 * @see de.nykon.bitcoin.sdk.value.Response
 */
class DeleteOrderBody {
    var errors: List<Errors> = ArrayList()
    var credits = 0
}