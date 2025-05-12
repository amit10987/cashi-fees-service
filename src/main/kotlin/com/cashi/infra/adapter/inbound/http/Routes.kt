import com.cashi.domain.model.Transaction
import com.cashi.infra.adapter.outbound.workflow.FeeResult
import com.cashi.infra.adapter.outbound.workflow.TransactionWorkflowClient
import com.cashi.infra.config.RestateClientProvider
import dev.restate.client.SendResponse
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.transactionRoutes() {
    post("/api/v1/transaction/fee") {
        // Receive and validate the Transaction object
        val transaction = call.receive<Transaction>()

        // Submit the transaction to the Restate workflow
        val workflowClient = TransactionWorkflowClient.fromClient(
            RestateClientProvider.client,
            transaction.transactionId
        )
        val submission: SendResponse<FeeResult> = workflowClient.submit(transaction)
        // Handle the result or timeout
        call.respond(HttpStatusCode.OK, submission.attach().response())
    }
}
