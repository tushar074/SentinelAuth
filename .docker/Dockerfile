# Use Eclipse Temurin 17 JRE as base image
FROM eclipse-temurin:17-jre-jammy

# Set working directory
WORKDIR /app

# Set environment variables
ENV APP_HOME=/app \
    APP_JAR=application.jar

# Copy JAR file from build context
COPY target/*.jar ${APP_JAR}

# Expose default Spring Boot port
EXPOSE 8080

# Health check configuration
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Set the entry point to run the Spring Boot JAR
ENTRYPOINT ["java", "-jar", "${APP_JAR}"]

# Default command (can be overridden)
CMD ["--spring.profiles.active=prod"]
