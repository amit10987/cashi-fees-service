package com.cashi.infra.config

import dev.restate.client.Client

object RestateClientProvider {

    @Volatile
    private var overriddenClient: Client? = null

    val restateClient: Client
        get() = overriddenClient ?: defaultClient

    private val defaultClient: Client by lazy {
        Client.connect("http://localhost:8080")
    }

    fun overrideClient(client: Client) {
        overriddenClient = client
    }

    fun reset() {
        overriddenClient = null
    }
}
