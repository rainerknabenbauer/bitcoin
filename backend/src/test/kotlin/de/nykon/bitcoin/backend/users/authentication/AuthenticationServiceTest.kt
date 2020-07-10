package de.nykon.bitcoin.backend.users.authentication

import de.nykon.bitcoin.backend.users.HashUtils
import de.nykon.bitcoin.backend.users.value.Authentication
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class AuthenticationServiceTest {

    @Autowired
    lateinit var testee: AuthenticationService

    @Value("\${default.user}")
    private lateinit var defaultUser: String

    @Value("\${default.password}")
    private lateinit var defaultPassword: String

    @Test
    fun `token gets generated as expected`() {
        // arrange

        val password = HashUtils.sha512(defaultPassword)
        val secret = "1234567890"
        val token = HashUtils.sha512(password.plus(secret))

        val authentication = Authentication(defaultUser, secret, token)

        // act
        val result = testee.authenticate(authentication)

        // assert
        SoftAssertions().apply {
            assertTrue(result)
        }
    }

}