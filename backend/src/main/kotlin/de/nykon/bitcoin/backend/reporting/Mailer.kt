package de.nykon.bitcoin.backend.reporting

import de.nykon.bitcoin.sdk.bitcoinDe.ShowAccountInfo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

/**
 * Sends an eMail containing relevant information to stakeholders.
 */
@Component
open class Mailer(
        private val showAccountInfo: ShowAccountInfo,
        private val myTradeHistoryService: MyTradeHistoryService
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Value("\${mail.recipients}")
    private lateinit var recipients: String

    @Value("\${spring.mail.host}")
    private lateinit var host: String

    @Value("\${spring.mail.username}")
    private lateinit var email: String

    @Value("\${spring.mail.password}")
    private lateinit var password: String

    /**
     * Sends the current state of affairs once a day.
     */
    @Scheduled(cron = "0 0 5 * * *")
    @Async
    open fun sendDailyNewsletter() {

        val accountInfo = showAccountInfo.execute()

        var message = this::class.java.getResource("/mail.html").readText(Charsets.UTF_8)
                .replace("{{BITCOINS}}", accountInfo.body.data.balances.btc.total_amount.toString())

        val newestTrades = myTradeHistoryService.getNewestTrades()

        newestTrades.forEach {

            var line = ""

            var dateTime = it.dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)
            dateTime = """$dateTime ${it.dateTime.format(DateTimeFormatter.ISO_LOCAL_TIME)}"""

            line += getTransactionRow().replaceFirst("{{DATE}}", dateTime)
                    .replaceFirst("{{CRYPTO_CURRENCY}}", it.cryptoCurrency.toString())
                    .replaceFirst("{{TYPE}}", it.type.toString())
                    .replaceFirst("{{PRICE}}", it.price.toString())
                    .replaceFirst("{{COINS}}", it.coins.toString())
                    .replaceFirst("{{MONEY_AMOUNT}}", it.moneyAmount.toString())
            line += "{{TRANSACTION_ROW}}"

            message = message.replaceFirst("{{TRANSACTION_ROW}}", line)
        }
        message = message.replace("{{TRANSACTION_ROW}}", "")

        send(recipients.split(",").toList(), message)
    }

    private fun send(recipients: List<String>, message: String) {
        val props = Properties()
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "false"
        props["mail.smtp.host"] = host
        props["mail.smtp.port"] = "587"

        val session: Session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(email, password)
            }
        })
        val msg: Message = MimeMessage(session)
        msg.setFrom(InternetAddress(email, false))

        log.info("Sending mail with message: $message")

        recipients.forEach {recipient ->

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient))
            msg.subject = "Tägliche Meldung"
            msg.setContent(message, "text/html; charset=UTF-8")
            msg.sentDate = Date()

            Transport.send(msg)

        }
    }

    fun getTransactionRow(): String {
        return """  
                    <tr>
                        <td>{{DATE}}</td>
                        <td>{{CRYPTO_CURRENCY}}</td>
                        <td>{{TYPE}}</td>
                        <td>{{PRICE}}</td>
                        <td>{{COINS}}</td>
                        <td>{{MONEY_AMOUNT}}</td>
                    </tr>
              """
    }

}