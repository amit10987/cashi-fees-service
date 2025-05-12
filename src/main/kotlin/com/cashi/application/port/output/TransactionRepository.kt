package com.cashi.application.port.output

import com.cashi.domain.model.Transaction
import com.cashi.domain.model.Fee

interface TransactionRepository {
    fun save(transaction: Transaction, fee: Fee)
    fun get(transactionId: String): Pair<Transaction, Fee>?
    fun count(): Int
}
