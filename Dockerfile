# Multi-stage build to optimize image size
FROM maven:3.8.6-openjdk-17 AS build

# Set working directory
WORKDIR /app

# Copy Maven configuration files
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Download dependencies (cache layer)
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Production image
FROM openjdk:17-jre-slim

# Install system dependencies
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Create non-root user for security
RUN groupadd -r harmonia && useradd -r -g harmonia harmonia

# Set working directory
WORKDIR /app

# Copy JAR from the build stage
COPY --from=build /app/target/harmonia-1.0-SNAPSHOT.jar app.jar

# Change file ownership
RUN chown -R harmonia:harmonia /app

# Switch to non-root user
USER harmonia

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"] 