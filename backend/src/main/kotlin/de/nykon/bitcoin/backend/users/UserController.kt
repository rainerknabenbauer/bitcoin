package de.nykon.bitcoin.backend.users

import de.nykon.bitcoin.backend.repository.value.Authentication
import de.nykon.bitcoin.backend.users.authentication.AuthenticationResponse
import de.nykon.bitcoin.backend.users.authentication.AuthenticationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
        private val authenticationService: AuthenticationService) {

    @PostMapping(path = ["/authenticate"])
    fun authenticate(@RequestBody authentication: Authentication): AuthenticationResponse {
        return authenticationService.authenticate(authentication)
    }

}