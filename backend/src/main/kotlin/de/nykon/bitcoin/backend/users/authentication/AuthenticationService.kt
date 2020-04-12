package de.nykon.bitcoin.backend.users.authentication

import de.nykon.bitcoin.backend.repository.UserRepository
import de.nykon.bitcoin.backend.repository.value.Authentication
import de.nykon.bitcoin.backend.repository.value.HashUtils
import de.nykon.bitcoin.backend.repository.value.Session
import de.nykon.bitcoin.backend.repository.value.User
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class AuthenticationService(
        private val userRepository: UserRepository
) {

    /**
     * Authenticates given credentials. Valid credentials will get a token returned.
     */
    fun authenticate(authentication: Authentication): AuthenticationResponse {
        val user = userRepository.findUserByName(authentication.name)

        val timestamp = System.currentTimeMillis().toString()

        if (isValid(authentication, user)) {

            val token = generateToken(user!!.password, timestamp)
            val authenticationResponse = AuthenticationResponse(true, timestamp)
            val session = Session(LocalDateTime.now(), token)

            val userWithSession = User(
                    user.id,
                    user.name,
                    user.password,
                    user.createdDate,
                    session
            )
            userRepository.save(userWithSession)
            return authenticationResponse
        }

        return AuthenticationResponse(
                false,
                timestamp)
    }

    private fun generateToken(password: String, timestamp: String): String {
        return HashUtils.sha512(password.plus(timestamp))
    }

    private fun isValid(authentication: Authentication, user: User?): Boolean {
        if (Objects.nonNull(user)) {
            return user!!.password == authentication.password
        }
        return false
    }

}