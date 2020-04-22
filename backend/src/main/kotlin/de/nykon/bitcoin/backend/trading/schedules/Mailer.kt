package de.nykon.bitcoin.backend.trading.schedules

import de.nykon.bitcoin.sdk.bitcoinDe.ShowAccountInfo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Component
open class Mailer(
        private val showAccountInfo: ShowAccountInfo
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Value("\${recipients}")
    private lateinit var recipients: String

    /**
     * Sends the current state of affairs once a day.
     */
    @Scheduled(fixedRate = 15000)
    @Async
    open fun sendDailyNewsletter() {

        val accountInfo = showAccountInfo.execute()

        val message = this::class.java.getResource("/mail.html").readText(Charsets.UTF_8)
                .replace("{{BITCOINS}}", accountInfo.body.data.balances.btc.total_amount.toString())

        send(recipients.split(",").toList(), message)
    }

    fun send(recipients: List<String>, message: String) {
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

        log.info("Sending mail with message: $message")

        recipients.forEach {recipient ->

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient))
            msg.subject = "Tutorials point email"
            msg.setContent(message, "text/html")
            msg.sentDate = Date()

            Transport.send(msg)

        }
    }

}