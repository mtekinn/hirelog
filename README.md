# HireLog - Job Application Tracker API

A RESTful API built with Spring Boot for tracking job applications. Never lose track of where you applied.

## Tech Stack

- Java 22
- Spring Boot 3.5
- Spring Security + JWT
- PostgreSQL
- Docker
- Maven

## Features

- User registration and login with JWT authentication
- BCrypt password hashing
- Track job applications with status updates
- Filter applications by user
- Financial summary with reply rate statistics

## Getting Started

### Prerequisites

- Java 22
- Docker

### Run the database
```bash
docker run --name hirelog-db -e POSTGRES_PASSWORD=1234 -e POSTGRES_DB=hirelogdb -p 5434:5432 -d postgres
```

### Run the application
```bash
./mvnw spring-boot:run
```

## API Endpoints

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | Register a new user |
| POST | /api/auth/login | Login and get JWT token |

### Applications
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/applications?userId={id} | Get all applications for a user |
| POST | /api/applications | Create a new application |
| PATCH | /api/applications/{id}/status | Update application status |
| DELETE | /api/applications/{id} | Delete an application |
| GET | /api/applications/summary/{userId} | Get application summary |

## Authentication

All endpoints except `/api/auth/**` require a JWT token:
```
Authorization: Bearer <token>
```

## Status Values

`APPLIED` `INTERVIEW` `OFFER` `REJECTED`