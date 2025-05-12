package com.cashi.domain.policy

import com.cashi.domain.model.Fee
import com.cashi.domain.model.Transaction
import com.cashi.domain.model.Type

class TransferFeePolicy(
    private val rate: Double = 0.002
) : FeeCalculationPolicy {

    override fun supports(type: Type): Boolean = type == Type.TRANSFER

    override fun calculate(transaction: Transaction): Fee {
        val feeAmount = transaction.amount * rate
        return Fee(
            amount = feeAmount,
            rate = rate,
            description = "Standard fee for TRANSFER at ${rate * 100}%"
        )
    }
}
