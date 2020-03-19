package de.nykon.bitcoin.client

import com.google.common.hash.Hashing
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.Charset
import java.security.MessageDigest
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter


val API_KEY : String = System.getenv("bitcoin.api.key")
val TIMESTAMP : String = System.currentTimeMillis().toString()
val URI = "https://api.bitcoin.de/v4/trades"

@ComponentScan
@EnableAutoConfiguration
open class ClientApplication

fun main() {

    val httpClient : HttpClient = HttpClient.newHttpClient()

    val httpRequest = HttpRequest.newBuilder()
            .GET()
            .setHeader("X-API-KEY", API_KEY)
            .setHeader("X-API-NONCE", TIMESTAMP)
            .setHeader("X-API-SIGNATURE", getHmacSignature())
            .uri(java.net.URI.create(URI))
            .build()

    val send = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())

    println(send)

}

fun getHmacSignature(): String {

    val hashingKey : SecretKey = SecretKeySpec(API_KEY.toByteArray(), "HmacSHA256")
    val hmacSha256 = Hashing.hmacSha256(hashingKey)

    return hmacSha256.hashString(getHmacData(), Charset.defaultCharset()).toString().toLowerCase()
}

fun getHmacData(): String {

    // Schritt 4: Konkatinieren der HMAC-Eingabedaten
    // GET#https://api.bitcoin.de/v4/trades#MY_API_KEY#timestamp#md5hash

    val httpMethod = "GET"
    val queryHash = hashMd5("")

    return "$httpMethod#$URI#$API_KEY#$TIMESTAMP#$queryHash";
}

fun hashMd5(requestParams: String): String {

    // Schritt 1: Aufsteigendes Sortieren der POST-Parameter anhand ihres Namens
    // Schritt 2: Einen validen URL-encoded Query-String aus den POST-Parametern generieren

    val md = MessageDigest.getInstance("MD5")
    md.update(requestParams.toByteArray())
    val digest = md.digest()!!

    // Schritt 3: md5-Hash über den in Schritt 2 erstellten Query-String der POST-Parameter bilden
    return DatatypeConverter.printHexBinary(digest).toLowerCase()
}

fun md5EncodePostParams(): String {

    // Schritt 1: Aufsteigendes Sortieren der POST-Parameter anhand ihres Namens
    // Schritt 2: Einen validen URL-encoded Query-String aus den POST-Parametern generieren
    val showMyTrades = "state=0&type=buy"

    val md = MessageDigest.getInstance("MD5")
    md.update(showMyTrades.toByteArray())
    val digest = md.digest()!!

    // Schritt 3: md5-Hash über den in Schritt 2 erstellten Query-String der POST-Parameter bilden
    return DatatypeConverter.printHexBinary(digest).toLowerCase()
}