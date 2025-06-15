# Spring Boot RESTful Web Service with Hibernate & JWT Security

[](https://spring.io/projects/spring-boot)

This repository features a robust, scalable, and secure **Spring Boot RESTful web service** for managing product data. It leverages **Hibernate and Spring Data JPA** for efficient data persistence and implements **JSON Web Token (JWT)** for modern, stateless API authentication. This project showcases a solid foundation for building secure and maintainable backend applications.

-----

## ✨ Features

  * **RESTful API Design:** Provides a clean set of **CRUD (Create, Read, Update, Delete)** operations for product management.
  * **Layered Architecture:** Clear separation of concerns (Controller, Service, Repository, Entity) for enhanced maintainability and testability.
  * **Robust Data Persistence:** Utilizes **Spring Data JPA** with **Hibernate** for seamless database interactions (PostgreSQL ready, H2 for dev).
  * **JWT-Based Security:** Implements industry-standard **JSON Web Token (JWT) authentication** for secure, stateless API access.
  * **Comprehensive Input Validation:** Integrates **JSR 380 (Bean Validation)** to ensure data integrity and security for all incoming requests.
  * **Global Exception Handling:** Centralized error management provides consistent and informative error responses.
  * **CORS Configuration:** Pre-configured Cross-Origin Resource Sharing policies for smooth frontend integration.
  * **Interactive API Documentation:** Automatically generated **Swagger UI** for easy API exploration and testing.

-----

## 🛠️ Technology Stack

  * **Backend:** Java 21, Spring Boot 3.5.0, Spring Data JPA, Hibernate 6.x
  * **Security:** Spring Security, JJWT (JSON Web Token library), BCrypt (for password encoding)
  * **Database:** PostgreSQL (Production), H2 Database (In-memory for Development/Testing)
  * **Build Tool:** Apache Maven 3.9.10
  * **API Documentation:** Springdoc OpenAPI / Swagger UI

-----

## 🚀 Getting Started

Follow these steps to set up and run the project locally.

### Prerequisites

  * **JDK 21** or later
  * **Apache Maven 3.x** or later

### 1\. Clone the Repository

```bash
git clone https://github.com/your-username/my-service.git
cd my-service
```

### 2\. Build the Project

Use Maven to clean and install dependencies:

```bash
mvn clean install
```

### 3\. Run the Application

You can run the application directly via Maven or using the generated JAR file:

```bash
# Option 1: Run with Maven
mvn spring-boot:run

# Option 2: Run the JAR (after mvn clean install)
java -jar target/my-service-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`.

-----

## 🌐 Accessing the API

### Swagger UI

Access the interactive API documentation and test endpoints directly via Swagger UI:
👉 [http://localhost:8080/swagger-ui.html](https://www.google.com/search?q=http://localhost:8080/swagger-ui.html)

### Public Endpoints

  * **GET all products:** `http://localhost:8080/api/products`
  * **GET product by ID:** `http://localhost:8080/api/products/{id}`

### Authenticated Endpoints (JWT Required)

For `POST`, `PUT`, and `DELETE` operations on `/api/products`, you'll need a JWT.

1.  **Obtain a JWT:**
    Send a `POST` request to `http://localhost:8080/api/auth/authenticate` with the following JSON body:

    ```json
    {
        "username": "user",
        "password": "password"
    }
    ```

    (Or "admin" / "adminpass" for admin roles).
    The response will contain your JWT in the `token` field.

2.  **Use the JWT for Authenticated Requests:**
    Include the obtained JWT in the `Authorization` header of your subsequent requests (e.g., for POSTing a new product):

    ```
    Authorization: Bearer <YOUR_JWT_TOKEN_HERE>
    Content-Type: application/json
    ```

    **Example: POST /api/products (to create a product)**

      * **URL:** `http://localhost:8080/api/products`
      * **Method:** `POST`
      * **Headers:** `Authorization: Bearer <your_jwt_token>`, `Content-Type: application/json`
      * **Body (JSON):**
        ```json
        {
            "name": "New Gaming Headset",
            "description": "High-fidelity audio with noise cancellation",
            "price": 99.99,
            "quantity": 200
        }
        ```

-----

## 🗄️ Database Setup

The application uses an in-memory H2 database by default for development convenience.
For a production environment, you can switch to **PostgreSQL** by updating `application.properties` and including the PostgreSQL driver dependency in `pom.xml`. The design document provides SQL scripts for setting up the PostgreSQL database and inserting sample data.

-----

## ✅ Best Practices Implemented

This project emphasizes a range of best practices to ensure a high-quality, maintainable, and secure application:

  * **Stateless Session Management:** Critical for scalable REST APIs.
  * **Strong Password Encoding:** Using BCrypt for secure credential storage.
  * **Centralized CORS Configuration:** Handles cross-origin requests cleanly.
  * **Structured Logging:** Prepared for easy integration with monitoring tools.
  * **Externalized Configuration:** JWT secret key and other properties are externalized.
  * **Clear Transaction Management:** Using `@Transactional` for data consistency.

-----

## 📜 [![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

This project is licensed under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0). 

-----
