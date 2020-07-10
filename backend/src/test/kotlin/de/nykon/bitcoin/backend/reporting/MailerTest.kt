package de.nykon.bitcoin.backend.reporting

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class MailerTest {

    @Autowired
    private lateinit var testee: Mailer

    @Test
    fun `send test email`() {
        // arrange

        // act
        testee.sendDailyNewsletter()

        // assert

    }

}