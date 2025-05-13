package com.cashi.testsupport

import com.cashi.app.module
import com.cashi.infra.config.RestateClientProvider
import dev.restate.client.Client
import io.ktor.client.*
import io.ktor.server.testing.*

fun runKtorWorkflowTest(
    ingressClient: Client,
    testBlock: suspend (
        client: HttpClient, builder: TestApplicationBuilder
    ) -> Unit,
) = testApplication {
    RestateClientProvider.overrideClient(ingressClient)
    application {
        module()
    }

    testBlock(this.client, this)
}
