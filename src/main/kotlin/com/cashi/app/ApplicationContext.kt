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
import com.typesafe.config.ConfigFactory

data class ApplicationContext(
    val transactionRepository: TransactionRepository,
    val useCase: ProcessTransactionUseCase,
    val workflow: TransactionWorkflow
)

fun createApplicationContext(
    customFeeCalculator: FeeCalculator? = null,
    customTransactionRepository: TransactionRepository? = null
): ApplicationContext {
    val config = ConfigFactory.load()
    val feeRates = config.getConfig("fee.rate")

    val transactionRepository = customTransactionRepository ?: InMemoryTransactionRepository()

    val feeCalculator = customFeeCalculator ?: FeeCalculator(
        listOf(
            StandardMobileTopUpFeePolicy(feeRates.getDouble("mobile-top-up")),
            TransferFeePolicy(feeRates.getDouble("transfer")),
            WithdrawalFeePolicy(feeRates.getDouble("withdrawal")),
            NoFeePolicy()
        )
    )

    val useCase: ProcessTransactionUseCase = TransactionProcessor(feeCalculator)
    val workflow = TransactionWorkflow(useCase, transactionRepository)

    return ApplicationContext(transactionRepository, useCase, workflow)
}
