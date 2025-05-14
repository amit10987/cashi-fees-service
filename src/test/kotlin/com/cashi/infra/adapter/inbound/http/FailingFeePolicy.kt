package com.cashi.infra.adapter.inbound.http

import com.cashi.domain.exception.NonTriableException
import com.cashi.domain.model.Fee
import com.cashi.domain.model.Transaction
import com.cashi.domain.model.Type
import com.cashi.domain.policy.FeeCalculationPolicy

class FailingTransferFeePolicy : FeeCalculationPolicy {
    override fun supports(type: Type): Boolean {
        return type == Type.TRANSFER
    }

    override fun calculate(transaction: Transaction): Fee {
        throw NonTriableException()
    }

}