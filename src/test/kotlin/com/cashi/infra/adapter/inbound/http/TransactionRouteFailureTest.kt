package com.cashi.infra.adapter.inbound.http

import com.cashi.app.createApplicationContext
import com.cashi.domain.fixture.aTransferTransaction
import com.cashi.domain.service.FeeCalculator
import com.cashi.testsupport.RestateIntegrationTestSupport
import com.cashi.testsupport.TestHttpClientUtils
import com.cashi.testsupport.runKtorWorkflowTest
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class TransactionRouteFailureTest : FeatureSpec({

    val context = createApplicationContext(
        customFeeCalculator = FeeCalculator(listOf(FailingTransferFeePolicy()))
    )
    val testSupport = RestateIntegrationTestSupport()

    beforeTest {
        testSupport.setupWith(context.workflow)
    }

    afterTest {
        context.transactionRepository.clear()
        testSupport.tearDown()
    }

    feature("Transaction Fee Calculation Failure") {
        scenario("Failing fee policy should return HTTP 500 and not persist transaction") {
            runKtorWorkflowTest(testSupport.client) { client, _ ->

                // GIVEN
                val transaction = aTransferTransaction().build()

                // WHEN
                val response: HttpResponse =
                    TestHttpClientUtils.postTransaction(client, Json.encodeToString(transaction))

                // THEN
                response.status shouldBe HttpStatusCode.InternalServerError
                context.transactionRepository.count() shouldBe 0
            }
        }
    }
})
