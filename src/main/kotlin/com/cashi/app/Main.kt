package com.cashi.app

import com.typesafe.config.ConfigFactory
import dev.restate.sdk.http.vertx.RestateHttpServer
import dev.restate.sdk.kotlin.endpoint.endpoint
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {

    // Load application.conf using Typesafe Config
    val config = ConfigFactory.load()
    val restatePort = config.getInt("restate.deployment.port")

    // Initialize application context
    val context = createApplicationContext()

    RestateHttpServer.listen(
        endpoint { bind(context.workflow) },
        restatePort
    )

    embeddedServer(Netty, configure = {
        val hoconConfig = HoconApplicationConfig(ConfigFactory.load())
        connector {
            port = hoconConfig.property("ktor.deployment.port").getString().toInt()
        }
    }) {
        module()
    }.start(wait = true)
}
