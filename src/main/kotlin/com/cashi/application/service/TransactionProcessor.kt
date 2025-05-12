package com.cashi.application.service

import com.cashi.application.port.input.ProcessTransactionUseCase
import com.cashi.domain.model.Fee
import com.cashi.domain.model.Transaction
import com.cashi.domain.service.FeeCalculator

class TransactionProcessor(
    private val feeCalculator: FeeCalculator,
) : ProcessTransactionUseCase {

    override fun execute(transaction: Transaction): Fee {
        return feeCalculator.calculate(transaction)
    }
}
