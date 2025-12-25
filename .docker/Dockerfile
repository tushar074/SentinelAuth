# Multi-stage build Dockerfile for SentinelAuth
# Stage 1: Builder - using Java 17 JDK for compilation
FROM eclipse-temurin:17-jdk-jammy AS builder

# Set working directory
WORKDIR /app

# Copy project files for building
COPY . .

# Build the application (adjust based on your build tool - Maven/Gradle)
# For Maven:
RUN apt-get update && apt-get install -y --no-install-recommends maven && rm -rf /var/lib/apt/lists/*
RUN mvn clean package -DskipTests -q

# Alternative for Gradle (comment out Maven section and uncomment below if using Gradle):
# RUN chmod +x gradlew && ./gradlew clean build -x test --quiet

# Stage 2: Runtime - using Java 17 JRE for minimal footprint
FROM eclipse-temurin:17-jre-jammy

# Install curl for health checks
RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*

# Create non-root user for security
RUN groupadd -r sentinelauth && useradd -r -g sentinelauth sentinelauth

# Set working directory
WORKDIR /app

# Copy built application from builder stage
# Adjust the source path based on your build output (typically target/app.jar for Maven)
COPY --from=builder --chown=sentinelauth:sentinelauth /app/target/*.jar application.jar

# Environment variables for SentinelAuth
ENV JAVA_OPTS="-XX:+UseG1GC -XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=25.0 -XX:+UnlockExperimentalVMOptions -XX:G1NewCollectionHeuristicPercent=30 -XX:G1ReservePercent=10"
ENV APP_NAME="SentinelAuth"
ENV APP_PORT="8080"
ENV APP_ENV="production"
ENV LOG_LEVEL="INFO"
ENV SPRING_PROFILES_ACTIVE="prod"

# Expose application port
EXPOSE ${APP_PORT}

# Switch to non-root user
USER sentinelauth

# Health check configuration
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:${APP_PORT}/actuator/health || exit 1

# Application startup command
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar application.jar"]

# Labels for metadata
LABEL maintainer="tushar074"
LABEL description="SentinelAuth - Multi-stage Docker image with Java 17 JRE runtime"
LABEL version="1.0"
