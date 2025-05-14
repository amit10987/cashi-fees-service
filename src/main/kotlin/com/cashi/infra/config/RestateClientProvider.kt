package com.cashi.infra.config

import com.typesafe.config.ConfigFactory
import dev.restate.client.Client
import org.slf4j.LoggerFactory

object RestateClientProvider {

    private val logger = LoggerFactory.getLogger(RestateClientProvider::class.java)

    @Volatile
    private var overriddenClient: Client? = null

    val restateClient: Client
        get() = overriddenClient ?: defaultClient

    private val defaultClient: Client by lazy {
        val config = ConfigFactory.load()
        val endpoint = config.getString("restate.client.endpoint")
        logger.info("Creating default Restate client with endpoint: $endpoint")
        Client.connect(endpoint)
    }

    fun overrideClient(client: Client) {
        overriddenClient = client
    }

    fun reset() {
        overriddenClient = null
    }
}
