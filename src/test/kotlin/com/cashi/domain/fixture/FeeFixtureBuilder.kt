package com.cashi.domain.fixture

import com.cashi.domain.model.Fee

class FeeFixtureBuilder {
    private var amount: Double = 1.5
    private var rate: Double = 0.0015
    private var description: String = "Standard"

    fun withAmount(amount: Double) = apply { this.amount = amount }
    fun withRate(rate: Double) = apply { this.rate = rate }
    fun withDescription(description: String) = apply { this.description = description }

    fun build(): Fee {
        return Fee(
            amount = amount,
            rate = rate,
            description = description
        )
    }
}

fun aFee(): FeeFixtureBuilder {
    return FeeFixtureBuilder()
}