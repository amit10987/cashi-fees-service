package com.cashi.infra.config

import dev.restate.client.Client

object RestateClientProvider {
    val client: Client by lazy {
        Client.connect("http://localhost:8080")
    }
}