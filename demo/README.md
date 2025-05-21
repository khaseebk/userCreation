
# Spring Boot JWT Secured API Demo

## Prerequisites:
- Java 17

## Getting Started
- Run it `./gradlew bootRun`
- Check the application has started successfully: `curl localhost:8080` or visit in a browser.

## 🔐 JWT Authentication Flow

This project demonstrates securing REST APIs using **Spring Boot Security + JWT**.

### 🔁 Flow Summary:
1. User logs in with `/api/v1/auth/login`
2. Backend validates credentials and returns a **JWT token**
3. Future API requests use `Authorization: Bearer <token>` header
4. A custom `JwtFilter` validates the token for protected endpoints

### 📂 JWT-Related Files:

| File | Description |
|------|-------------|
| `JwtUtil.java` | Token generation and validation logic |
| `JwtFilter.java` | Intercepts HTTP requests, validates JWT token |
| `AuthController.java` | Exposes login endpoint |
| `SecurityConfig.java` | Configures permitted paths, adds filters, etc. |
| `LoginRequest.java` | Maps login payload (`username`, `password`) |

### 🔑 Example: Login & Use Token

```
curl -X POST http://localhost:8080/api/v1/auth/login \
-H "Content-Type: application/json" \
-d '{"username": "admin", "password": "password"}'
```

Use token in header:
```
curl -H "Authorization: Bearer <your-token>" http://localhost:8080/api/v1/users
```

---

## 🗃️ H2 In-Memory Database Console

**H2 Console** is enabled for quick data viewing.

### 🔧 Configuration (`application.properties`):

```
spring.datasource.url=jdbc:h2:mem:demo_db
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.show-sql=true
```

### 🌐 Access:
- Visit: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL: `jdbc:h2:mem:demo_db`
- User: `sa`, Password: `password`

> ✅ Make sure `/h2-console/**` is whitelisted in `SecurityConfig.java`

### 🧾 Users.java Overview

- The `Users.java` file defines a model class for user entities.
- Annotated with `@Entity`, it automatically maps to a table in the H2 database.
- Fields like `id`, `name`, and `email` are translated to columns.
- When the app starts, the table is created automatically in memory based on this model.

```java
@Entity
public class Users {
    @Id
    private Long id;
    private String name;
    private String email;
    // Getters and Setters
}
```

You can explore this table via the H2 Console.

---

## 🚨 Global Exception Handler

### 🧾 File: `GlobalExceptionHandler.java`
Handles exceptions thrown from service or controller layer.

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong");
    }
}
```

---

## 📌 Example cURL Commands for User API

```
curl -X POST http://localhost:8080/api/v1/users \
-H "Content-Type: application/json" \
-d '{"id":"101","name":"Alice","email":"alice@example.com"}'

curl -X POST http://localhost:8080/api/v1/users/create \
-d id=102 -d "name=Ali" -d "email=ali@example.com"

curl -X POST http://localhost:8080/api/v1/users/createUsingRequestBody \
-H "Content-Type: application/json" \
-d '{"id":"103","name":"Bob","email":"bob@example.com"}'
```

List of all users:
```
curl -X GET http://localhost:8080/api/v1/users
```

Get single user by ID (PathVariable):
```
curl -X GET "http://localhost:8080/api/v1/users/101"
```

Create user from browser:
```
http://localhost:8080/api/v1/users/createFromURLUsingGetMapping?id=104&name=Imo&email=imo@example.com
```

Delete by ID (PathVariable):
```
curl -X DELETE "http://localhost:8080/api/v1/users/101"
```

Delete All:
```
curl -X DELETE http://localhost:8080/api/v1/users
```

Update user:
```
curl -X PUT http://localhost:8080/api/v1/users \
-H "Content-Type: application/json" \
-d '{"id":101,"name":"NewName"}'
```

---

## ✅ Done!
This project combines **JWT-based authentication**, **H2 in-memory DB**, and **global error handling** using best practices.
