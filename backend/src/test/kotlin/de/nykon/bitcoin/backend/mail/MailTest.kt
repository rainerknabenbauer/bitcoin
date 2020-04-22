package de.nykon.bitcoin.backend.mail

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


@SpringBootTest
open class MailTest {

    @Value("\${recipients}")
    private lateinit var recipients: String

    @Test
    fun `demo send mail`() {

        val props = Properties()
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.smtp.host"] = "w018eb81.kasserver.com"
        props["mail.smtp.port"] = "587"

        val session: Session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication("btc@nykon.de", "B5g3dLr4REgJvRNu")
            }
        })
        val msg: Message = MimeMessage(session)
        msg.setFrom(InternetAddress("btc@nykon.de", false))

        val json = this::class.java.getResource("/mail.html").readText(Charsets.UTF_8)

        recipients.split(",").forEach {recipient ->

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient))
            msg.subject = "Tutorials point email"
            msg.setContent(json, "text/html")
            msg.sentDate = Date()

            Transport.send(msg)

        }

    }

}