# Cashi Fees Service

## Overview
Cashi Fees Service is a Kotlin-based application designed to calculate transaction fees for various types of transactions. It follows clean architecture principles, ensuring a modular and maintainable codebase.

## Technologies Used
- **Kotlin**: Primary programming language.
- **Gradle**: Build automation tool.
- **Ktor**: Framework for building asynchronous servers and clients.
- **Kotest**: Testing framework for unit and integration tests.
- **Kotlin Serialization**: For JSON serialization and deserialization.
- **Docker**: Used for containerization and deployment.

## Project Structure
The project is organized into the following layers:
- **Domain**: Contains core business logic and models.
- **Application**: Handles use cases and application-specific logic.
- **Infra**: Manages external integrations like persistence and workflows.
- **Tests**: Includes unit and integration tests.

## Prerequisites
- **Java 21**
- **Gradle** (or use the provided `gradlew` wrapper)
- **Docker** (required for running the Restate server and the application in a containerized environment)
- **Restate Server** (must be running for the application to function properly)

## How to Build the Project
1. Clone the repository:
   ```bash
   git clone https://github.com/amit10987/cashi-fees-service.git
   cd cashi-fees-service
   ```
2. Build the project using Gradle:
   ```bash
   ./gradlew build
   ```

## How to Run the Application
1. Start the application using Gradle:
   ```bash
   ./gradlew run
   ```
2. Start only the Restate server using Docker Compose from the root location:
   ```bash
   docker-compose -f docker/docker-compose.yml up restate-server
   ```
3. Register the service with the Restate server:
   ```bash
   curl -X POST http://localhost:9070/deployments \
     -H "Content-Type: application/json" \
     -d '{"uri": "http://host.docker.internal:9080"}'
   ```
4. Alternatively, you can run the application and Restate server using Docker Compose from the root location:
   ```bash
   docker-compose -f docker/docker-compose.yml up --build
   ```

## How to Run Test Cases
1. Run all tests using Gradle:
   ```bash
   ./gradlew test
   ```
2. View test reports:
   - After running the tests, reports will be available in `build/reports/tests/test/index.html`.

## Key Test Cases
- **Integration Tests**:
  - `TransactionRouteIntegrationTest`: Verifies transaction fee calculation and API behavior.
- **Failure Tests**:
  - `TransactionRouteFailureTest`: Ensures proper handling of failure scenarios.

## Sample Request and Response

### Request
```bash
curl --location 'http://localhost:8181/api/v1/transaction/fee' \
--header 'Content-Type: application/json' \
--data '{
    "transactionId": "txn1",
    "amount": 10000,
    "asset": "USD",
    "assetType": "FIAT",
    "type": "MOBILE_TOP_UP",
    "state": "SETTLED_PENDING_FEE",
    "createdAt": "2023-08-30T15:42:17.610059"
}'
```

### Response
```json
{
    "transactionId": "txn1",
    "amount": 10000.0,
    "asset": "USD",
    "type": "MOBILE TOP UP",
    "fee": 15.0,
    "rate": 0.0015,
    "description": "Standard fee rate of 0.15%"
}
```

## Additional Notes
- The application uses Kotlin's official code style for consistency.
- Ensure Docker is installed and running if you plan to use the `docker-compose` setup.
