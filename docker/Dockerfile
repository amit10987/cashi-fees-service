# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-slim

# Set the working directory in the container
WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradlew gradlew.bat settings.gradle.kts build.gradle.kts gradle.properties ./
COPY gradle/ ./gradle/

# Copy source code
COPY src/ ./src/

# Build the application without running tests
RUN ./gradlew build -x test --no-daemon

# Copy the application JAR file into the container
COPY build/libs/cashi-fees-service-all.jar app.jar

# Expose the port the application runs on
EXPOSE 8181

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
