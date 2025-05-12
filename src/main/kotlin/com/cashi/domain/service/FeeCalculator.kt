package com.cashi.domain.service

import com.cashi.domain.model.Fee
import com.cashi.domain.model.Transaction
import com.cashi.domain.policy.FeeCalculationPolicy

class FeeCalculator(
    private val policies: List<FeeCalculationPolicy>
) {
    fun calculate(transaction: Transaction): Fee {
        val policy = policies.find { it.supports(transaction.type) }
            ?: throw IllegalArgumentException("No fee policy found for transaction type: ${transaction.type}")
        return policy.calculate(transaction)
    }
}
