package com.cashi.testsupport

import com.typesafe.config.ConfigFactory

object FeeRateLoader {
    private val feeRates = ConfigFactory.load().getConfig("fee.rate")

    fun rateFor(type: String): Double = feeRates.getDouble(type)
}
