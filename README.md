# HireLog - Job Application Tracker API

A production-ready RESTful API built with Spring Boot for tracking job applications. Deployed on Railway with Redis caching, rate limiting, and CI/CD pipeline.

🔗 **Live API:** https://hirelog-production.up.railway.app  
📖 **Swagger UI:** https://hirelog-production.up.railway.app/swagger-ui/index.html

## Tech Stack

- **Java 22** + **Spring Boot 3.5**
- **Spring Security** + **JWT Authentication**
- **PostgreSQL** — persistent storage
- **Redis** — caching & rate limiting
- **Docker** + **Docker Compose**
- **GitHub Actions** — CI/CD pipeline
- **Railway** — cloud deployment

## Features

- JWT-based authentication with BCrypt password hashing
- Track job applications with status updates (APPLIED, INTERVIEW, OFFER, REJECTED)
- Redis caching for application queries — reduces DB load
- Redis-based rate limiting — 60 requests/minute per IP
- Application summary with reply rate statistics
- Swagger UI for interactive API documentation
- Automated CI/CD with GitHub Actions
- Deployed to Railway with managed PostgreSQL and Redis

## Getting Started

### Prerequisites

- Java 22
- Docker

### Run locally
```bash
# Clone the repository
git clone https://github.com/mtekinn/hirelog.git
cd hirelog

# Copy and configure environment variables
cp src/main/resources/application.properties.example src/main/resources/application.properties
# Edit application.properties with your values

# Start PostgreSQL and Redis
docker-compose up -d

# Run the application
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

### System
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /health | Health check |

## Authentication

All endpoints except `/api/auth/**` require a JWT token:
```
Authorization: Bearer <token>
```

## Status Values

`APPLIED` `INTERVIEW` `OFFER` `REJECTED`

## Architecture
```
Client → Rate Limit Filter (Redis) → JWT Filter → Controller → Service → Repository → PostgreSQL
                                                                    ↕
                                                                  Redis Cache
```

## Running Tests
```bash
./mvnw test
```