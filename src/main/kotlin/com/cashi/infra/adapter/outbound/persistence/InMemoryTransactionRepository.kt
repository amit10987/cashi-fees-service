package com.cashi.infra.adapter.outbound.persistence

import com.cashi.application.port.output.TransactionRepository
import com.cashi.domain.model.Fee
import com.cashi.domain.model.Transaction
import java.util.concurrent.ConcurrentHashMap

class InMemoryTransactionRepository : TransactionRepository {

    private val store = ConcurrentHashMap<String, Pair<Transaction, Fee>>()

    override fun save(transaction: Transaction, fee: Fee) {
        store[transaction.transactionId] = Pair(transaction, fee)
    }

    override fun get(transactionId: String): Pair<Transaction, Fee>? {
        return store[transactionId]
    }

    override fun count(): Int = store.count()
}
