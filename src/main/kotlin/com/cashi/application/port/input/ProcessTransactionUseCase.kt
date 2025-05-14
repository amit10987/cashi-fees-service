package com.cashi.application.port.input

import com.cashi.domain.model.Fee
import com.cashi.domain.model.Transaction

fun interface ProcessTransactionUseCase {
    fun execute(transaction: Transaction): Fee
}
