package com.cashi.domain.fixture

import com.cashi.domain.model.AssetType
import com.cashi.domain.model.State
import com.cashi.domain.model.Transaction
import com.cashi.domain.model.Type

class TransactionFixtureBuilder {
    private var transactionId: String = "txn-default"
    private var amount: Double = 100.0
    private var asset: String = "USD"
    private var assetType: AssetType = AssetType.FIAT
    private var type: Type = Type.TRANSFER
    private var state: State = State.SETTLED_PENDING_FEE
    private var createdAt: String = "2024-01-01T12:00:00Z"

    fun withTransactionId(transactionId: String) = apply { this.transactionId = transactionId }
    fun withAmount(amount: Double) = apply { this.amount = amount }
    fun withAsset(asset: String) = apply { this.asset = asset }
    fun withAssetType(assetType: AssetType) = apply { this.assetType = assetType }
    fun withType(type: Type) = apply { this.type = type }
    fun withState(state: State) = apply { this.state = state }
    fun withCreatedAt(createdAt: String) = apply { this.createdAt = createdAt }

    fun build(): Transaction {
        return Transaction(
            transactionId = transactionId,
            amount = amount,
            asset = asset,
            assetType = assetType,
            type = type,
            state = state,
            createdAt = createdAt
        )
    }
}

fun aTransaction(): TransactionFixtureBuilder {
    return TransactionFixtureBuilder()
}

fun aMobileTopUpTransaction(): TransactionFixtureBuilder {
    return aTransaction().withType(Type.MOBILE_TOP_UP)
}

fun aTransferTransaction(): TransactionFixtureBuilder {
    return aTransaction().withType(Type.TRANSFER)
}

fun aWithdrawalTransaction(): TransactionFixtureBuilder {
    return aTransaction().withType(Type.WITHDRAWAL)
}