package com.cashi.app

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import transactionRoutes

fun Application.module() {
    // Set up Content Negotiation with JSON (using Kotlinx serialization)
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = true
            explicitNulls = false
        })
    }

    routing {
        route("/api/v1") {
            transactionRoutes()
        }
    }
}
