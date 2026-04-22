## About the Project

- Experimental REST API for music lovers, with roles "Artist" and "User", allowed reviews about albums and singles
  inspiration at last.fm.

- Developed using layered architecture (Controller - Service - DTO - Repository)

## Tech Stack

- Java 17
- Spring Boot
- Spring Data Jpa (hibernate)
- Spring Security
- Lombok
- MySQL
- Swagger API

## Features

- JWT-based authentication
- Role-based authorization (Artist and User)
- CRUD Operations for Artist, User, Track, Album, Single and Review
- Public review system
- Pin albums and tracks (obsession)
- Create playlists
- Invite others users to your playlist
- Profile management (update info and password

## Requirements

- Java 17+
- MySQL

## Running the project

```bash
git clone <https://github.com/davithekid/midnights_fm>
cd midnights_fm
```

---

## Documentation

| Documentation                                          | Description        |
|--------------------------------------------------------|--------------------|
| [Swagger](http://localhost:8080/swagger-ui/index.html) | API Endpoint       |
| [Database](./database.md)                              | Structure Entities |