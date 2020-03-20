package de.nykon.bitcoin.client

import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class BackendClient {

    fun persist(json: String, cycleInMilliseconds: Long) {

        val uri = "http://localhost:8888/offers"

        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(
                "application/json".toMediaTypeOrNull(), json)

        val request: Request = Request.Builder()
                .url(uri)
                .addHeader("cycle-in-ms", cycleInMilliseconds.toString())
                .post(body)
                .build()

        val call: Call = client.newCall(request)
        call.execute()
    }

}