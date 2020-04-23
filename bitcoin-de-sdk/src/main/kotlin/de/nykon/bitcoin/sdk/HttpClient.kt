package de.nykon.bitcoin.sdk

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

class RestClient {

    private val rest: RestTemplate = RestTemplate()
    private val headers: HttpHeaders = HttpHeaders()

    operator fun get(uri: String): ResponseEntity<String> {
        val requestEntity: HttpEntity<String> = HttpEntity<String>("", headers)
        return rest.exchange(uri, HttpMethod.GET, requestEntity, String::class.java)
    }

    fun post(uri: String, json: String): ResponseEntity<String> {
        val requestEntity: HttpEntity<String> = HttpEntity<String>(json, headers)
        return rest.exchange(uri, HttpMethod.POST, requestEntity, String::class.java)
    }

    fun put(uri: String, json: String): ResponseEntity<String> {
        val requestEntity: HttpEntity<String> = HttpEntity<String>(json, headers)
        return rest.exchange(uri, HttpMethod.PUT, requestEntity, String::class.java)
    }

    fun delete(uri: String): ResponseEntity<String> {
        val requestEntity: HttpEntity<String> = HttpEntity<String>("", headers)
        return rest.exchange(uri, HttpMethod.DELETE, requestEntity, String::class.java)
    }

    init {
        headers.add("Content-Type", "application/json")
        headers.add("Accept", "*/*")
    }
}