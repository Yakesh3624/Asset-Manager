## Asset Management System

A full-stack Asset Management application built using Spring Boot (Microservices), Angular, MySQL. This system enables organizations to manage, track, and audit assets efficiently.

## Table of Contents

- Features
- Tech Stack
- Microservice Architecture
- Getting Started
- Frontend (Angular)
- Backend (Spring Boot)
- Docker Setup
- API Documentation
- Author

## Features

- CRUD operations for Assets, Users, Audits, Services and Requests
- Microservice architecture with RESTful APIs
- Angular frontend UI with responsive design
- Dockerized deployment
- MySQL database integration
- Model mapping using ModelMapper

## Tech Stack

### Backend:
- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security (OAuth2/JWT)
- Hibernate
- ModelMapper
- MySQL

### Frontend:
- Angular 15
- Bootstrap
- RxJS, Reactive Forms

### DevOps:
- Docker
- SonarQube

## Microservice Architecture

Frontend (Angular) --> API Gateway --> AssetManagement MS | Users MS

Each microservice runs independently and communicates via REST APIs.

## Getting Started

### Prerequisites

- Java 21
- Node.js & Angular CLI
- Maven
- Docker
- MySQL
- Git

## Frontend (Angular)

### Setup

1. Navigate to frontend folder
2. Run:
   npm install
   ng serve

App runs at: http://localhost:4200

## Backend (Spring Boot)

### Setup (for each microservice)

1. Navigate to microservice folder
2. Run -> Run as Spring Boot

App runs at: http://localhost:8080 (users-microservice)
App runs at: http://localhost:8081 (AssetManagement)

## Docker Setup

Pull all the Docker images to your Docker Desktop and run them manually or with docker-compose.

### Docker Hub Repositories

- Frontend: https://hub.docker.com/repository/docker/yakesh3624/docker-frontend
- Service 1: https://hub.docker.com/repository/docker/yakesh3624/docker-service1
- Service 2: https://hub.docker.com/repository/docker/yakesh3624/docker-service2

### Pull Docker Images

```bash
docker pull yakesh3624/docker-frontend
docker pull yakesh3624/docker-service1
docker pull yakesh3624/docker-service2
```

## API Documentation

The API is documented using Swagger.

- Visit: http://localhost:8080/swagger-ui/index.html
- Format: OpenAPI 3.0
- You can test endpoints, see schemas, and export the spec.


## Author

Yakesh Balaji Raja .P
GitHub: https://github.com/Yakesh3624
Email: yakeshraja34@gmail.com
