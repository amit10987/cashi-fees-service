package com.cashi.infra.adapter.inbound.http

import com.cashi.app.createApplicationContext
import com.cashi.domain.fixture.aFee
import com.cashi.domain.fixture.aMobileTopUpTransaction
import com.cashi.domain.fixture.aTransferTransaction
import com.cashi.infra.adapter.outbound.workflow.FeeResult
import com.cashi.testsupport.FeeRateLoader
import com.cashi.testsupport.RestateIntegrationTestSupport
import com.cashi.testsupport.TestHttpClientUtils
import com.cashi.testsupport.runKtorWorkflowTest
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class TransactionRouteIntegrationTest : FeatureSpec({

    val context = createApplicationContext()
    val testSupport = RestateIntegrationTestSupport()

    beforeTest {
        testSupport.setupWith(context.workflow)
    }

    afterTest {
        context.transactionRepository.clear()
        testSupport.tearDown()
    }

    feature("Transaction Fee Calculation") {
        scenario("Client posts a valid mobile top-up transaction") {
            runKtorWorkflowTest(testSupport.client) { client, _ ->
                // GIVEN
                val transaction = aMobileTopUpTransaction().build()
                val rate = FeeRateLoader.rateFor("mobile-top-up")
                val expectedFee = aFee()
                    .withAmount(transaction.amount * rate)
                    .withRate(rate)
                    .build()

                // WHEN
                val response = TestHttpClientUtils.postTransaction(client, Json.encodeToString(transaction))
                val feeResult = Json.decodeFromString<FeeResult>(response.bodyAsText())

                // THEN
                response.status shouldBe HttpStatusCode.OK
                feeResult.transactionId shouldBe transaction.transactionId
                feeResult.fee shouldBe expectedFee.amount
                feeResult.rate shouldBe expectedFee.rate

                context.transactionRepository.count() shouldBe 1
            }
        }
        scenario("Client posts a valid transfer transaction") {
            runKtorWorkflowTest(testSupport.client) { client, _ ->
                // GIVEN
                val transaction = aTransferTransaction().build()
                val rate = FeeRateLoader.rateFor("transfer")
                val expectedFee = aFee()
                    .withAmount(transaction.amount * rate)
                    .withRate(rate)
                    .build()

                // WHEN
                val response = TestHttpClientUtils.postTransaction(client, Json.encodeToString(transaction))
                val feeResult = Json.decodeFromString<FeeResult>(response.bodyAsText())

                // THEN
                response.status shouldBe HttpStatusCode.OK
                feeResult.transactionId shouldBe transaction.transactionId
                feeResult.fee shouldBe expectedFee.amount
                feeResult.rate shouldBe expectedFee.rate

                context.transactionRepository.count() shouldBe 1
            }
        }

        scenario("Posting the same transaction twice should be idempotent") {
            runKtorWorkflowTest(testSupport.client) { client, _ ->
                // GIVEN
                val transactionBuilder = aMobileTopUpTransaction()
                val transaction = transactionBuilder.build()
                val rate = FeeRateLoader.rateFor("mobile-top-up")
                val expectedFee = aFee()
                    .withAmount(transaction.amount * rate)
                    .withRate(rate)
                    .build()

                // WHEN - first request
                val firstResponse = TestHttpClientUtils.postTransaction(client, Json.encodeToString(transaction))
                val firstFeeResult = Json.decodeFromString<FeeResult>(firstResponse.bodyAsText())

                // WHEN - second request with same transaction ID
                val secondResponse = TestHttpClientUtils.postTransaction(client, Json.encodeToString(transaction))
                val secondFeeResult = Json.decodeFromString<FeeResult>(secondResponse.bodyAsText())

                // THEN - both responses are equal
                firstResponse.status shouldBe HttpStatusCode.OK
                secondResponse.status shouldBe HttpStatusCode.OK

                firstFeeResult shouldBe secondFeeResult
                secondFeeResult.transactionId shouldBe transaction.transactionId
                secondFeeResult.fee shouldBe expectedFee.amount
                secondFeeResult.rate shouldBe expectedFee.rate

                // THEN - only one transaction persisted
                context.transactionRepository.count() shouldBe 1
            }
        }

        scenario("Client posts a request with an empty body") {
            runKtorWorkflowTest(testSupport.client) { client, _ ->
                // WHEN
                val response: HttpResponse = TestHttpClientUtils.postTransaction(client, "{}")

                // THEN
                response.status shouldBe HttpStatusCode.BadRequest

                // THEN - no transaction persisted
                context.transactionRepository.count() shouldBe 0
            }
        }

    }
})
