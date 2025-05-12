package com.cashi.domain.policy

import com.cashi.domain.model.Fee
import com.cashi.domain.model.Transaction
import com.cashi.domain.model.Type

class StandardMobileTopUpFeePolicy(
    private val rate: Double = 0.0015
) : FeeCalculationPolicy {

    override fun supports(type: Type): Boolean = type == Type.MOBILE_TOP_UP

    override fun calculate(transaction: Transaction): Fee {
        val feeAmount = transaction.amount * rate
        return Fee(
            amount = feeAmount,
            rate = rate,
            description = "Standard fee rate of ${rate * 100}%"
        )
    }
}
