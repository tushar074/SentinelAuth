# SentinelAuth

A comprehensive authentication and authorization framework designed to provide robust security solutions for modern applications.

## Table of Contents

- [Project Overview](#project-overview)
- [Quick Start Guide](#quick-start-guide)
- [Profile Documentation](#profile-documentation)
- [API Testing Examples](#api-testing-examples)
- [Build Instructions](#build-instructions)
- [Troubleshooting](#troubleshooting)

## Project Overview

SentinelAuth is a powerful authentication system that provides:

- **Secure User Authentication**: Industry-standard authentication mechanisms with support for multiple authentication strategies
- **Role-Based Access Control (RBAC)**: Fine-grained permission management through role-based authorization
- **Token Management**: JWT-based token generation and validation with configurable expiration policies
- **Multi-Factor Authentication**: Support for additional security layers including 2FA and biometric authentication
- **Audit Logging**: Comprehensive logging of authentication and authorization events
- **Session Management**: Robust session handling with timeout and termination capabilities
- **API Security**: Protection against common security vulnerabilities (CSRF, XSS, etc.)

### Key Features

- RESTful API architecture
- Microservices compatible
- Database agnostic (supports multiple databases)
- Extensive configuration options
- Production-ready security standards
- Comprehensive error handling

## Quick Start Guide

### Prerequisites

- Java 11 or higher (or relevant runtime for your implementation)
- Maven 3.6+ or Gradle 6.0+ (depending on project setup)
- Docker (optional, for containerized deployment)
- PostgreSQL/MySQL (for default database setup)

### Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/tushar074/SentinelAuth.git
   cd SentinelAuth
   ```

2. **Configure Environment**
   ```bash
   cp .env.example .env
   # Edit .env with your configuration details
   ```

3. **Build the Project**
   ```bash
   # Using Maven
   mvn clean install
   
   # Using Gradle
   gradle clean build
   ```

4. **Run the Application**
   ```bash
   # Using Maven
   mvn spring-boot:run
   
   # Using Gradle
   gradle bootRun
   
   # Using Docker
   docker build -t sentinelauth:latest .
   docker run -p 8080:8080 sentinelauth:latest
   ```

5. **Verify Installation**
   ```bash
   curl http://localhost:8080/api/health
   ```

### Basic Usage

```bash
# User Registration
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "email": "user@example.com",
    "password": "SecurePassword123!"
  }'

# User Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "password": "SecurePassword123!"
  }'
```

## Profile Documentation

### Development Profile

**Configuration**: `application-dev.yml`

```yaml
server:
  port: 8080
  servlet:
    context-path: /api

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/sentinelauth_dev
    username: root
    password: dev_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

logging:
  level:
    root: INFO
    com.sentinelauth: DEBUG
```

**Usage**:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

### Production Profile

**Configuration**: `application-prod.yml`

```yaml
server:
  port: 8443
  ssl:
    key-store: classpath:keystore.p12
    key-store-password: ${KEYSTORE_PASSWORD}

spring:
  datasource:
    url: jdbc:mysql://${DB_HOST}:3306/${DB_NAME}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate

logging:
  level:
    root: WARN
    com.sentinelauth: INFO
```

**Usage**:
```bash
export SPRING_PROFILES_ACTIVE=prod
mvn spring-boot:run
```

### Testing Profile

**Configuration**: `application-test.yml`

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: create-drop

logging:
  level:
    root: WARN
```

## API Testing Examples

### Authentication Endpoints

#### Register a New User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "StrongPassword123!",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

#### Login User
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "StrongPassword123!"
  }'

# Response includes JWT token
# {
#   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
#   "refreshToken": "...",
#   "expiresIn": 3600
# }
```

#### Verify Token
```bash
curl -X POST http://localhost:8080/api/auth/verify \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json"
```

#### Refresh Token
```bash
curl -X POST http://localhost:8080/api/auth/refresh \
  -H "Content-Type: application/json" \
  -d '{
    "refreshToken": "<REFRESH_TOKEN>"
  }'
```

#### Logout
```bash
curl -X POST http://localhost:8080/api/auth/logout \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json"
```

### User Management Endpoints

#### Get Current User Profile
```bash
curl -X GET http://localhost:8080/api/users/me \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

#### Update User Profile
```bash
curl -X PUT http://localhost:8080/api/users/{userId} \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "newemail@example.com",
    "firstName": "Jane",
    "lastName": "Smith"
  }'
```

#### Change Password
```bash
curl -X POST http://localhost:8080/api/users/{userId}/change-password \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "currentPassword": "OldPassword123!",
    "newPassword": "NewPassword456!"
  }'
```

### Role & Permission Endpoints

#### Assign Role to User
```bash
curl -X POST http://localhost:8080/api/users/{userId}/roles \
  -H "Authorization: Bearer <ADMIN_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "roleId": "admin"
  }'
```

#### Get User Permissions
```bash
curl -X GET http://localhost:8080/api/users/{userId}/permissions \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

#### Create New Role
```bash
curl -X POST http://localhost:8080/api/roles \
  -H "Authorization: Bearer <ADMIN_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "moderator",
    "description": "Moderator role with limited admin capabilities",
    "permissions": ["read:users", "update:users", "delete:comments"]
  }'
```

### Testing with Postman

1. Import the provided Postman collection: `postman_collection.json`
2. Set environment variables:
   - `base_url`: http://localhost:8080
   - `jwt_token`: (auto-populated after login)
   - `refresh_token`: (auto-populated after login)
3. Execute requests in sequence to test the full workflow

## Build Instructions

### Using Maven

```bash
# Clean build
mvn clean build

# Build with specific profile
mvn clean build -P prod

# Build JAR file
mvn clean package

# Build and skip tests
mvn clean package -DskipTests

# Build with all checks (tests, code coverage, code quality)
mvn clean verify
```

### Using Gradle

```bash
# Clean build
gradle clean build

# Build with specific profile
gradle build --args='--spring.profiles.active=prod'

# Build JAR file
gradle bootJar

# Build and skip tests
gradle build -x test

# Build with all checks
gradle build --continue
```

### Docker Build

```bash
# Build Docker image
docker build -t sentinelauth:latest .

# Build with specific tag
docker build -t sentinelauth:v1.0.0 .

# Run Docker container
docker run -d \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DB_HOST=postgres \
  -e DB_USER=admin \
  -e DB_PASSWORD=password \
  sentinelauth:latest

# Docker Compose (if available)
docker-compose up -d
```

### Build Artifacts

After successful build, the following artifacts are generated:

- **JAR File**: `target/sentinelauth-*.jar`
- **WAR File**: `target/sentinelauth-*.war` (if configured)
- **Docker Image**: `sentinelauth:latest`
- **Test Reports**: `target/surefire-reports/`
- **Code Coverage**: `target/site/jacoco/`

## Troubleshooting

### Common Issues

#### 1. Database Connection Error

**Error**: `java.sql.SQLException: Cannot get a connection`

**Solutions**:
- Verify database server is running
  ```bash
  # Check MySQL
  mysql -u root -p -e "SELECT 1;"
  
  # Check PostgreSQL
  psql -U postgres -c "SELECT 1;"
  ```
- Check database credentials in `.env` or `application.yml`
- Ensure database exists:
  ```sql
  CREATE DATABASE sentinelauth_dev;
  ```
- Verify network connectivity to database host
- Check firewall rules for database port (3306 for MySQL, 5432 for PostgreSQL)

#### 2. Port Already in Use

**Error**: `Address already in use: bind`

**Solutions**:
```bash
# Find process using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>

# Or use different port
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

#### 3. Authentication Token Expired

**Error**: `401 Unauthorized: Invalid or expired token`

**Solutions**:
- Use refresh token endpoint to get new token
- Check token expiration time in configuration
- Ensure system clock is synchronized
- Verify JWT secret key in configuration

#### 4. Permission Denied Error

**Error**: `403 Forbidden: Insufficient permissions`

**Solutions**:
- Verify user has required role assigned
- Check role permissions configuration
- Ensure token includes correct claims
- Review RBAC configuration for the resource

#### 5. Build Failure

**Error**: `BUILD FAILURE`

**Solutions**:
```bash
# Check Java version
java -version

# Ensure correct JDK is set
export JAVA_HOME=/path/to/jdk11

# Clean build cache
mvn clean

# Build with verbose output
mvn clean build -X

# Check Maven dependencies
mvn dependency:tree
```

#### 6. Docker Container Won't Start

**Error**: `Container exits immediately after startup`

**Solutions**:
```bash
# Check logs
docker logs <container_id>

# Run with interactive terminal
docker run -it sentinelauth:latest

# Verify environment variables
docker run -it --env-file .env sentinelauth:latest
```

### Debug Mode

Enable debug logging:

```bash
# Environment variable
export LOG_LEVEL=DEBUG

# Application properties
logging.level.com.sentinelauth=DEBUG
logging.level.org.springframework.security=DEBUG

# Run with debug flag
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```

### Health Check

```bash
# Basic health check
curl http://localhost:8080/api/health

# Detailed health check
curl http://localhost:8080/api/health/details

# Check database health
curl http://localhost:8080/api/health/db
```

### Performance Tuning

- Increase heap size: `-Xmx2g -Xms1g`
- Configure connection pool size
- Enable query caching
- Optimize database indexes
- Use load balancing for production

### Getting Help

- Check the [Issues](https://github.com/tushar074/SentinelAuth/issues) page
- Review [Discussions](https://github.com/tushar074/SentinelAuth/discussions)
- Read the [Wiki](https://github.com/tushar074/SentinelAuth/wiki)
- Contact the development team

---

**Last Updated**: 2025-12-25

For more information, visit the [GitHub Repository](https://github.com/tushar074/SentinelAuth)
