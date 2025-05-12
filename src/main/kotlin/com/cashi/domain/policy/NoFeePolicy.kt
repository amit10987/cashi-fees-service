package com.cashi.domain.policy

import com.cashi.domain.model.Fee
import com.cashi.domain.model.Transaction
import com.cashi.domain.model.Type

class NoFeePolicy : FeeCalculationPolicy {

    override fun supports(type: Type): Boolean = true

    override fun calculate(transaction: Transaction): Fee {
        return Fee(
            amount = 0.0,
            rate = 0.0,
            description = "No fee applied for unsupported transaction type"
        )
    }
}
