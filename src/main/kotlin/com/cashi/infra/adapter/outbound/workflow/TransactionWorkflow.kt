package com.cashi.infra.adapter.outbound.workflow

import com.cashi.application.port.input.ProcessTransactionUseCase
import com.cashi.application.port.output.TransactionRepository
import com.cashi.domain.model.Transaction
import dev.restate.sdk.annotation.Workflow
import dev.restate.sdk.kotlin.WorkflowContext
import dev.restate.sdk.kotlin.runBlock

@Workflow
class TransactionWorkflow(
    private val useCase: ProcessTransactionUseCase,
    private val transactionRepository: TransactionRepository
) {

    @Workflow
    suspend fun run(ctx: WorkflowContext, transaction: Transaction): FeeResult {
        val fee = useCase.execute(transaction)
        ctx.runBlock {
            transactionRepository.save(transaction, fee)
        }
        return FeeResult.from(transaction, fee)
    }
}