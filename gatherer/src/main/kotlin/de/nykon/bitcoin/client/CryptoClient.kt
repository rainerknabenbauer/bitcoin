package de.nykon.bitcoin.client

import com.google.common.hash.Hashing
import java.nio.charset.Charset
import java.security.MessageDigest
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter

class CryptoClient {

    /**
     * Uses API secret.
     */
    fun getHmacSignature(hmachData: String, apiSecret: String): String {

        val hashingKey: SecretKey = SecretKeySpec(apiSecret.toByteArray(), "HmacSHA256")
        val hmacSha256 = Hashing.hmacSha256(hashingKey)

        return hmacSha256.hashString(hmachData, Charset.defaultCharset()).toString().toLowerCase()
    }

    /**
     * Generates input string for HMAC hashing.
     * Uses API Key.
     */
    fun getHmacData(
            httpMethod: String,
            uri: String,
            apiKey: String,
            nonce: String,
            md5Hash: String)
            : String {

        // Schritt 4: Konkatinieren der HMAC-Eingabedaten
        return "$httpMethod#$uri#$apiKey#$nonce#$md5Hash";
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

}