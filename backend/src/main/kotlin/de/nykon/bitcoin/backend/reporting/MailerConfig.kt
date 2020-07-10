package de.nykon.bitcoin.backend.reporting

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
open class MailerConfig {

    @Value("\${mail.recipients}")
    lateinit var recipients: String

    @Value("\${spring.mail.host}")
    lateinit var host: String

    @Value("\${spring.mail.username}")
    lateinit var email: String

    @Value("\${spring.mail.password}")
    lateinit var password: String

    @Value("\${spring.mail.properties.mail.smtp.starttls}")
    var starttls: Boolean = false


}