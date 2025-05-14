# -------- Stage 1: Build --------
FROM openjdk:21-slim AS builder

WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradlew gradlew.bat settings.gradle.kts build.gradle.kts gradle.properties ./
COPY gradle/ ./gradle/
COPY src/ ./src/

# Build the application
RUN ./gradlew shadowJar --no-daemon || ./gradlew build -x test --no-daemon

# -------- Stage 2: Run --------
FROM openjdk:21-slim

WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/build/libs/cashi-fees-service-all.jar app.jar

EXPOSE 8181

ENTRYPOINT ["java", "-jar", "app.jar"]
