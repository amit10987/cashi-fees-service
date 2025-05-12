package com.cashi.domain.policy

import com.cashi.domain.model.Fee
import com.cashi.domain.model.Transaction
import com.cashi.domain.model.Type

class WithdrawalFeePolicy(
    private val flatFee: Double = 5.0
) : FeeCalculationPolicy {

    override fun supports(type: Type): Boolean = type == Type.WITHDRAWAL

    override fun calculate(transaction: Transaction): Fee {
        return Fee(
            amount = flatFee,
            rate = 0.0,
            description = "Flat fee for WITHDRAWAL: $flatFee"
        )
    }
}
