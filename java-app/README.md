# 📚 BookAPI - Spring Boot REST API Microservice

**Phase 1: Java Spring Boot Microservice Foundation**

A simple Book Management REST API built with Spring Boot 3.x and Java 21, using Maven as the build tool.

---

## 🎯 Overview

This microservice provides REST API endpoints for managing books. It's designed to be:
- ✅ **Simple**: Easy to understand and modify
- ✅ **Production-ready**: Includes health checks, logging, and error handling
- ✅ **Well-tested**: Comprehensive unit tests included
- ✅ **Docker-ready**: Prepared for containerization (Phase 2)

---

## 📋 Prerequisites

Before you begin, ensure you have:

- **Java 21+** - `java -version`
- **Maven 3.6+** - `mvn --version`
- **Git** - `git --version`

---

## 🏗️ Project Structure

```
java-app/
├── pom.xml                                    # Maven configuration
├── README.md                                  # This file
│
├── src/main/java/com/example/bookapi/
│   ├── BookApiApplication.java                # Main application class
│   ├── controller/
│   │   └── BookController.java                # REST endpoints
│   ├── service/
│   │   └── BookService.java                   # Business logic
│   └── model/
│       └── Book.java                          # Data model
│
├── src/main/resources/
│   └── application.properties                 # Application configuration
│
└── src/test/java/com/example/bookapi/
    └── controller/
        └── BookControllerTest.java            # Unit tests
```

---

## 🚀 Getting Started

### 1. Build the Project

```bash
# Navigate to java-app directory
cd java-app

# Clean and build
mvn clean package

# Output: target/bookapi-1.0.0.jar
```

### 2. Run the Application

**Option A: Using Maven (Recommended for Development)**

```bash
mvn spring-boot:run
```

**Option B: Using JAR File**

```bash
java -jar target/bookapi-1.0.0.jar
```

### 3. Verify it's Running

The application will start on `http://localhost:8080`

Open your browser or use curl:

```bash
curl http://localhost:8080/api/books/hello
```

Expected output:
```json
{
  "message": "Hello from BookAPI!",
  "status": "API is running",
  "timestamp": 1729970000000
}
```

---

## 📡 REST API Endpoints

### Health Check Endpoint
```bash
# Check if app is running
GET /health
curl http://localhost:8080/health
```

### Book Management Endpoints

#### 1. Get Hello Message (Test Endpoint)
```bash
GET /api/books/hello

curl http://localhost:8080/api/books/hello
```

**Response (200 OK):**
```json
{
  "message": "Hello from BookAPI!",
  "status": "API is running",
  "timestamp": 1729970000000
}
```

---

#### 2. Get All Books
```bash
GET /api/books

curl http://localhost:8080/api/books
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "title": "Java Programming",
    "author": "John Doe",
    "year": 2022,
    "available": true
  },
  {
    "id": 2,
    "title": "Spring Boot Guide",
    "author": "Jane Smith",
    "year": 2023,
    "available": true
  }
]
```

---

#### 3. Get Book by ID
```bash
GET /api/books/{id}

curl http://localhost:8080/api/books/1
```

**Response (200 OK):**
```json
{
  "id": 1,
  "title": "Java Programming",
  "author": "John Doe",
  "year": 2022,
  "available": true
}
```

**Response (404 Not Found):**
```json
{
  "error": "Book not found",
  "id": 999
}
```

---

#### 4. Create a New Book
```bash
POST /api/books
Content-Type: application/json

curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "New Book",
    "author": "New Author"
  }'
```

**Request Body:**
```json
{
  "title": "New Book Title",
  "author": "New Author",
  "year": 2024,
  "available": true
}
```

**Response (201 Created):**
```json
{
  "id": 4,
  "title": "New Book Title",
  "author": "New Author",
  "year": 2024,
  "available": true
}
```

---

#### 5. Update a Book
```bash
PUT /api/books/{id}
Content-Type: application/json

curl -X PUT http://localhost:8080/api/books/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Updated Title",
    "available": false
  }'
```

**Request Body (send only fields to update):**
```json
{
  "title": "Updated Title",
  "available": false
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "title": "Updated Title",
  "author": "John Doe",
  "year": 2022,
  "available": false
}
```

---

#### 6. Delete a Book
```bash
DELETE /api/books/{id}

curl -X DELETE http://localhost:8080/api/books/1
```

**Response (200 OK):**
```json
{
  "message": "Book deleted successfully",
  "id": 1
}
```

**Response (404 Not Found):**
```json
{
  "error": "Book not found",
  "id": 999
}
```

---

#### 7. Get Book Statistics
```bash
GET /api/books/stats/summary

curl http://localhost:8080/api/books/stats/summary
```

**Response (200 OK):**
```json
{
  "totalBooks": 3,
  "availableBooks": 2,
  "unavailableBooks": 1
}
```

---

## 🧪 Running Tests

### Run All Tests

```bash
# Run all unit tests
mvn test

# Output:
# [INFO] -------------------------------------------------------
# [INFO]  T E S T S
# [INFO] -------------------------------------------------------
# [INFO] Running com.example.bookapi.controller.BookControllerTest
# [INFO] Tests run: 14, Failures: 0, Errors: 0, Skipped: 0
```

### Run Specific Test

```bash
mvn test -Dtest=BookControllerTest
```

### Run Tests with Coverage (Optional)

```bash
# Add JaCoCo plugin to pom.xml first, then:
mvn test jacoco:report
```

---

## 📊 Maven Lifecycle Commands

```bash
# Clean build artifacts
mvn clean

# Compile source code
mvn compile

# Run unit tests
mvn test

# Package into JAR
mvn package

# Full lifecycle: clean, compile, test, package
mvn clean package

# Run the application
mvn spring-boot:run

# View dependency tree
mvn dependency:tree

# Build with specific profile (if configured)
mvn clean package -P prod
```

---

## 🔧 Configuration

### Application Properties

Edit `src/main/resources/application.properties`:

```properties
# Server
server.port=8080                    # Change port here
server.shutdown=graceful            # Graceful shutdown

# Logging
logging.level.root=INFO             # Root log level
logging.level.com.example.bookapi=DEBUG  # App-specific level

# Actuator (Health checks, metrics)
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=always
```

### Change Server Port

```properties
# Change from 8080 to 9090
server.port=9090
```

Then restart the application.

---

## 📝 Sample CURL Commands

Create a test script `test-api.sh`:

```bash
#!/bin/bash

BASE_URL="http://localhost:8080"

echo "=== Testing BookAPI ==="
echo ""

echo "1. Hello Endpoint"
curl -s $BASE_URL/api/books/hello | jq .
echo ""

echo "2. Get All Books"
curl -s $BASE_URL/api/books | jq .
echo ""

echo "3. Get Book by ID"
curl -s $BASE_URL/api/books/1 | jq .
echo ""

echo "4. Create New Book"
curl -s -X POST $BASE_URL/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"DevOps Guide","author":"DevOps Team"}' | jq .
echo ""

echo "5. Update Book"
curl -s -X PUT $BASE_URL/api/books/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Updated Java Guide"}' | jq .
echo ""

echo "6. Get Statistics"
curl -s $BASE_URL/api/books/stats/summary | jq .
echo ""

echo "=== Testing Complete ==="
```

Run it:
```bash
bash test-api.sh
```

---

## 🐳 Docker (Phase 2 - Next)

This application is ready to be containerized. In Phase 2, we'll create:
- `Dockerfile` - Container configuration
- `docker-compose.yml` - Multi-container setup
- Deploy to Docker Hub registry

---

## 📊 Useful Endpoints for Monitoring

### Health Check (Spring Boot Actuator)
```bash
curl http://localhost:8080/health
```

### Application Info
```bash
curl http://localhost:8080/info
```

### Metrics
```bash
curl http://localhost:8080/metrics
```

---

## 🐛 Troubleshooting

### Port Already in Use
```bash
# Find process using port 8080
lsof -i :8080

# Kill process (Linux/Mac)
kill -9 <PID>

# Or change port in application.properties
server.port=9090
```

### Maven Build Fails
```bash
# Clear Maven cache
mvn clean
rm -rf ~/.m2/repository

# Rebuild
mvn clean package
```

### Java Version Mismatch
```bash
# Check Java version
java -version

# Should be 21 or higher
# If not, update JAVA_HOME environment variable
```

### Tests Fail
```bash
# Run tests with verbose output
mvn test -X

# Run specific test with output
mvn test -Dtest=BookControllerTest -e
```

---

## 📚 Phase 1 Summary

What we've built:
- ✅ Spring Boot REST API application
- ✅ 3 main endpoints (GET /hello, GET /api/books, POST /api/books, etc.)
- ✅ Service layer for business logic
- ✅ In-memory data storage
- ✅ Comprehensive unit tests
- ✅ Error handling with proper HTTP status codes
- ✅ Health check endpoints

---

## 🚀 Next Steps (Phase 2)

In Phase 2, we will:
1. Create a `Dockerfile` for containerization
2. Build Docker image: `docker build -t bookapi:v1.0.0 .`
3. Run container: `docker run -p 8080:8080 bookapi:v1.0.0`
4. Push to Docker Hub: `docker push username/bookapi:v1.0.0`

---

## 📖 Learning Resources

- [Spring Boot Official Docs](https://spring.io/projects/spring-boot)
- [Spring Boot REST API Guide](https://spring.io/guides/gs/rest-service/)
- [Maven Official Documentation](https://maven.apache.org/guides/)
- [Java 21 Features](https://openjdk.org/projects/jdk/21/)

---

## 📞 Questions?

If you have any questions about this Phase 1 implementation, feel free to ask!

---

**Phase 1 Status**: ✅ COMPLETE

Next Phase: Phase 2 - Docker Containerization Basics
