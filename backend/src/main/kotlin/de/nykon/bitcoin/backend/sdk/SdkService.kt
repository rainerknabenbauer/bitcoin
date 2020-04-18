package de.nykon.bitcoin.backend.sdk

import de.nykon.bitcoin.backend.users.authentication.AuthenticationService
import de.nykon.bitcoin.backend.users.value.Authentication
import de.nykon.bitcoin.sdk.bitcoinDe.ShowOrderbook
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SdkService(private val authenticationService: AuthenticationService) {

    @Value("\${bitcoin.apiKey}")
    private lateinit var apiKey: String

    @Value("\${bitcoin.apiSecret}")
    private lateinit var apiSecret: String


    fun showOrderbook(authentication: Authentication): ShowOrderbook? {
        return if (authenticationService.authenticate(authentication)) {
            ShowOrderbook(apiKey, apiSecret)
        } else {
            null
        }
    }

}