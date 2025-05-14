package com.cashi.testsupport

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

object TestHttpClientUtils {
    private const val ENDPOINT = "/api/v1/transaction/fee"
    suspend fun postTransaction(client: HttpClient, transactionJson: String): HttpResponse =
        client.post(ENDPOINT) {
            contentType(ContentType.Application.Json)
            setBody(transactionJson)
        }
}