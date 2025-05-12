package com.cashi.infra.adapter.outbound.workflow

import com.cashi.domain.model.Fee
import com.cashi.domain.model.Transaction
import kotlinx.serialization.Serializable

@Serializable
data class FeeResult(
    val transactionId: String,
    val amount: Double,
    val asset: String,
    val type: String,
    val fee: Double,
    val rate: Double,
    val description: String
) {
    companion object {
        fun from(transaction: Transaction, fee: Fee): FeeResult {
            return FeeResult(
                transactionId = transaction.transactionId,
                amount = transaction.amount,
                asset = transaction.asset,
                type = transaction.type.name.replace('_', ' '),
                fee = fee.amount,
                rate = fee.rate,
                description = fee.description
            )
        }
    }
}