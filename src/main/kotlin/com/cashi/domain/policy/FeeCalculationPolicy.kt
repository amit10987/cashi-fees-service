package com.cashi.domain.policy

import com.cashi.domain.model.Fee
import com.cashi.domain.model.Transaction
import com.cashi.domain.model.Type

interface FeeCalculationPolicy {
    fun supports(type: Type): Boolean
    fun calculate(transaction: Transaction): Fee
}