package com.cashi.app

import com.cashi.application.port.input.ProcessTransactionUseCase
import com.cashi.application.port.output.TransactionRepository
import com.cashi.application.service.TransactionProcessor
import com.cashi.domain.policy.NoFeePolicy
import com.cashi.domain.policy.StandardMobileTopUpFeePolicy
import com.cashi.domain.policy.TransferFeePolicy
import com.cashi.domain.policy.WithdrawalFeePolicy
import com.cashi.domain.service.FeeCalculator
import com.cashi.infra.adapter.outbound.persistence.InMemoryTransactionRepository
import com.cashi.infra.adapter.outbound.workflow.TransactionWorkflow
import com.cashi.infra.config.configureSerialization
import dev.restate.sdk.http.vertx.RestateHttpServer
import dev.restate.sdk.kotlin.endpoint.endpoint
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import transactionRoutes

fun main() {
    val persister: TransactionRepository = InMemoryTransactionRepository()
    val feeCalculator = FeeCalculator(
        listOf(
            StandardMobileTopUpFeePolicy(),
            TransferFeePolicy(),
            WithdrawalFeePolicy(),
            NoFeePolicy()
        )
    )
    val useCase: ProcessTransactionUseCase = TransactionProcessor(feeCalculator)

    RestateHttpServer.listen(endpoint { bind(TransactionWorkflow(useCase, persister)) })

    // Start Ktor server
    embeddedServer(Netty, port = 8181) {
        module()
    }.start(wait = false)
}

fun Application.module() {
    configureSerialization()
    routing {
        transactionRoutes()
    }
}
