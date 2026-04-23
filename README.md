## About the Project

- Experimental REST API for music lovers, with roles "Artist" and "User", allowing users to review albums and tracks, inspired by Last.fm.

- Developed using layered architecture (Controller - Service - DTO - Repository)

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- Spring Security
- Spring Boot Actuator 
- Lombok
- MySQL
- Swagger API
- Docker

## Features

- JWT-based authentication + Refresh Token
- Rate limiting
- Structured logging
- Role-based authorization (Artist and User)
- CRUD Operations for Artist, User, Track, Album, Review, Obsession, Playlist and Invite
- Public review system
- Pin albums and tracks (obsession)
- Create playlists
- Invite others users to your playlist
- Profile management (update info and password)

## Authentication Flow

- User logs in with username and password
- Server generates:
  - Access Token (JWT)
  - Refresh Token (stored in database)
- Access token is used for authenticated requests
- When expired:
  - Client sends refresh token
  - Server validates and issues a new access token

# Observability

- Spring Boot Actuator
- /actuator/health
- /actuator/metrics

# Testing

- Unit tests with JUnit and Mockito
- Integration tests with Spring Boot and H2 database

## Requirements

- Java 17+
- MySQL or Docker

## Running the project

```bash
git clone <https://github.com/davithekid/midnights_fm>
cd midnights_fm
```

## Running with docker
docker-compose up --build

---

## Documentation

| Documentation                                          | Description        |
|--------------------------------------------------------|--------------------|
| [Swagger](http://localhost:8080/swagger-ui/index.html) | API Endpoint       |
| [Database](./database.md)                              | Structure Entities |