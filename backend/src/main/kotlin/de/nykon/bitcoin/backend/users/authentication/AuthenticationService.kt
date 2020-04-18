package de.nykon.bitcoin.backend.users.authentication

import de.nykon.bitcoin.backend.users.HashUtils
import de.nykon.bitcoin.backend.users.UserRepository
import de.nykon.bitcoin.backend.users.value.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
        private val userRepository: UserRepository
) {

    fun authenticate(authentication: Authentication): Boolean {
        val user = userRepository.findUserByName(authentication.name)

        if (Objects.nonNull(user)) {

            val token = generateToken(user!!.password, authentication.secret)
            return token == authentication.token
        }
        return false
    }

    private fun generateToken(password: String, timestamp: String): String {
        return HashUtils.sha512(password.plus(timestamp))
    }
}