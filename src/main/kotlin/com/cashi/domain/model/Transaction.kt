package com.cashi.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val transactionId: String,
    val amount: Double,
    val asset: String,
    val assetType: AssetType,
    val type: Type,
    val state: State,
    val createdAt: String
)

@Serializable
enum class AssetType {
    FIAT, CRYPTO
}

@Serializable
enum class Type {
    MOBILE_TOP_UP, TRANSFER, WITHDRAWAL
}

@Serializable
enum class State {
    SETTLED_PENDING_FEE, SETTLED, FAILED
}
