# 🏨 The South Hotel – Backend API

This is the **backend REST API** for **The South Hotel**, a hotel booking application built using **Spring Boot**. It provides secure user authentication, room listing and filtering, booking functionality, and admin-level user and room management.

---

## 🚀 Features

- 🔐 **User Authentication** (JWT based)
- 🧑 **User Registration & Login**
- 🛏️ **Room Management**
  - Add, edit, delete rooms (Admin)
  - View available rooms by date and location
- 📅 **Booking System**
  - Book room by date and user
  - View user booking history
- 📦 **DTO-Based API Responses**
- 🌐 **CORS Configured** for frontend integration
- ☁️ **AWS S3 Integration** for storing images (optional)

---

## ⚙️ Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Security (JWT)**
- **Spring Data JPA**
- **MySQL / H2 (for testing)**
- **Maven**
- **AWS S3 (Optional - for profile images or room images)**

---

## 📁 Project Structure

src/
├── main/
│ ├── java/com/rishi/TheSouthHotel/
│ │ ├── controller/ # REST API controllers
│ │ ├── dto/ # DTO classes for clean API responses
│ │ ├── entity/ # JPA entities (User, Room, Booking)
│ │ ├── repo/ # Spring Data JPA repositories
│ │ ├── service/ # Service interfaces and implementations
│ │ ├── security/ # JWT and Spring Security configuration
│ │ └── utils/ # Utility classes (e.g., JWT utils)
│ └── resources/
│ └── application.properties
└── test/ # Unit and integration tests 

---

## 📦 API Endpoints Overview

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/auth/register` | Register a new user |
| `POST` | `/auth/login` | Authenticate and get JWT |
| `GET`  | `/rooms/all` | Get all available rooms |
| `GET`  | `/rooms/{id}` | Get details of a specific room |
| `POST` | `/bookings/bookRoom/{roomId}/{userId}` | Book a room |
| `GET`  | `/bookings/user/{userId}` | Get all bookings for a user |
| `GET`  | `/admin/users` | (Admin) Get all users |
| `DELETE` | `/admin/users/{id}` | (Admin) Delete a user |
| `POST` | `/rooms/add` | (Admin) Add a new room |

> Full Postman collection coming soon.

---

## 🔐 Authentication

This project uses **JWT (JSON Web Token)** for securing endpoints.

### 🔑 Getting a Token

1. Register using `/auth/register`
2. Login with `/auth/login`
3. Use the returned token in the `Authorization` header:


---

## 🛠️ Configuration (application.properties)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/southhotel
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=your-jwt-secret-key
jwt.expiration=3600000

