package com.cashi.testsupport

import dev.restate.client.Client
import dev.restate.sdk.endpoint.Endpoint
import dev.restate.sdk.testing.RestateRunner

class RestateIntegrationTestSupport {

    private lateinit var restateRunner: RestateRunner
    lateinit var client: Client
        private set

    fun setupWith(service: Any): Client {
        val endpoint = Endpoint.builder().bind(service).build()
        restateRunner = RestateRunner.from(endpoint).build()
        restateRunner.start()

        client = Client.connect(restateRunner.restateUrl.toString())
        return client
    }

    fun tearDown() {
        restateRunner.stop()
    }
}

