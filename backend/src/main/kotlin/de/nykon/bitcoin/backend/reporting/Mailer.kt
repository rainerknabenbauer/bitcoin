package de.nykon.bitcoin.backend.reporting

import de.nykon.bitcoin.backend.trade.buyer.BuyerConfig
import de.nykon.bitcoin.backend.trade.gatherer.repository.KrakenSummaryRepository
import de.nykon.bitcoin.backend.trade.gatherer.value.KrakenSummary
import de.nykon.bitcoin.backend.trade.seller.SellerConfig
import de.nykon.bitcoin.sdk.bitcoinDe.ShowAccountInfo
import de.nykon.bitcoin.sdk.cryptowatch.ShowKrakenSummary
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.math.BigDecimal
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
        private val config: MailerConfig,
        private val showAccountInfo: ShowAccountInfo,
        private val myTradeHistoryService: MyTradeHistoryService,
        private val sellerConfig: SellerConfig,
        private val buyerConfig: BuyerConfig,
        private val krakenSummaryRepository: KrakenSummaryRepository
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Sends the current state of affairs once a day.
     */
    @Scheduled(cron = "0 0 5 * * *")
    @Async
    open fun sendDailyNewsletter() {

        val accountInfo = showAccountInfo.execute()
        val krakenSummary = krakenSummaryRepository.findFirstByOrderByDateTimeDesc()

        var message = this::class.java.getResource("/mail.html").readText(Charsets.UTF_8)
                .replace("{{BITCOINS}}", accountInfo.body.data.balances.btc.total_amount.toString())
                .replace("{{PRICE}}", krakenSummary.last.toPlainString())

        message = if (krakenSummary.change.absolute.compareTo(BigDecimal.ZERO) == 1) {
            message.replace("{{TREND}}", "oben")
        } else {
            message.replace("{{TREND}}", "unten")
        }

        message = message.replace("{{TREND_VALUE}}", krakenSummary.change.absolute.toPlainString())

        message = if (sellerConfig.isAutomatized) {
            message.replace("{{AUTOMATIC_SALE}}","<br>Verkauf startet automatisch bei einem Kurs von ${sellerConfig.targetPrice}.")
        } else {
            message.replace("{{AUTOMATIC_SALE}}","")
        }

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

        send(config.recipients.split(",").toList(), message)
    }

    private fun send(recipients: List<String>, message: String) {
        val props = Properties()
        props["mail.smtp.auth"] = "true"
        //props["mail.smtp.starttls.enable"] = config.starttls
        props["mail.smtp.host"] = config.host
        props["mail.smtp.port"] = "587"

        val session: Session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(config.email, config.password)
            }
        })
        val msg: Message = MimeMessage(session)
        msg.setFrom(InternetAddress(config.email, false))

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