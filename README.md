# Seven Arts Travel — Backend  ![CircleCI](https://dl.circleci.com/status-badge/img/gh/CarinaPorumb/seven-arts-travel-backend/tree/main.svg?style=svg)


**Seven Arts Travel** is an application that aims to become a cultural travel guide. It is designed for those who wish to
discover the richness of cultural diversity through traveling. The seven arts - **Architecture, Sculpture,
Painting, Literature, Cinema, Music, Ballet and Theater** - are the focus of this project.
This application provides information not only on places such as historical buildings, museums, art galleries, libraries
and more, but also on events related to the seven arts, including exhibitions, performances, festivals and more.

This backend application powers the [Seven Arts Travel - Frontend](https://github.com/CarinaPorumb/seven-arts-travel-frontend) by exposing a RESTful API.

--- 

## Project Overview

Originally developed as a monolithic MVC app with Thymeleaf, this project is now being modernized to adopt a REST + SPA architecture.

- The backend is being migrated to a RESTful API structure.
- The frontend is now implemented in React with Vite.

---

## ⚠️ Notes

This backend is still under active development and may have temporary bugs or incomplete functionality.

---

## Tech Stack

- **Java 21**
- **Spring Boot 3**
- **Spring Data JPA**
- **Spring Security**
- **MySQL**
- **MapStruct**, **Lombok**, **OpenCSV**

---

## Features

* REST API for managing art objects, artists, movements, locations, and events
* DTO-based architecture with service and controller layers
* CSV-based data import at application startup
* Filtering, pagination, and search support
* User authentication & role-based authorization (in progress)

---

## Getting Started

### 1. Clone the project

```bash
   git clone https://github.com/CarinaPorumb/seven-arts-travel-backend.git
```

### 2. Configure MySQL

Ensure you have a MySQL instance running and set the appropriate credentials in `application.properties` or use environment variables.

### 3. Run the application

```bash
   ./mvnw spring-boot:run
``` 

The API will be available at: `http://localhost:8080/api`

---
