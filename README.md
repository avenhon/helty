
# Health Tracking Application 🚀

> **Status:** MVP

Backend REST API for tracking daily health metrics, built with **Java**, **Spring Boot**, and **PostgreSQL**. The application allows users to record daily health data, view their metrics, and receive personalized health insights.

## Technologies

- Java 21
- Spring Boot
- Spring Web
- Spring Security
- Spring Data JPA
- PostgreSQL
- Docker
- Maven

---  

## Features

### User Features

- User authentication and authorization
- View personal profile
- Add daily health metrics
- View today's metrics
- View history of recorded metrics
- Receive personalized daily health insights

### Admin Features

- View all users
- View user information
- View all recorded metrics
- Create metrics for any user (admin endpoint)

---  

## REST API

### User

| Method | Endpoint | Description |  
|---------|----------|-------------|  
| GET | `/api/v1/users/me` | Get current user |  

### Metrics

| Method | Endpoint | Description |  
|---------|----------|-------------|  
| GET | `/api/v1/metrics` | Get all metrics of authenticated user |  
| GET | `/api/v1/metrics/today` | Get today's metrics |  
| POST | `/api/v1/metrics` | Create daily metrics |  

### Insights

| Method | Endpoint | Description |  
|---------|----------|-------------|  
| GET | `/api/v1/insights` | Get personalized daily health insights |  

### Admin

| Method | Endpoint | Description |  
|---------|----------|-------------|  
| GET | `/api/v1/admin/users` | Get all users |  
| GET | `/api/v1/admin/user/{id}` | Get user by ID |  
| GET | `/api/v1/admin/metrics` | Get all metrics |  
| GET | `/api/v1/admin/metrics/{id}` | Get metrics by ID |  
| POST | `/api/v1/admin/metrics/admin` | Create metrics for any user |  
  
---  

## Project Structure

```  
src  
├── controller  
├── service  
├── repository  
├── entity  
├── dto  
├── mapper  
├── security  
├── exception  
└── config  
```  
  
---  

## Database

The application uses **PostgreSQL** as the primary database.

Main entities:

- User
- Metrics

Each user can have multiple daily metric records.
  
---  

## Running the Project

### Requirements

- Java 21
- Maven
- Docker
- PostgreSQL (or Docker)

### Clone the repository

```bash  
git clone https://github.com/avenhon/helty.git
cd helty
```  

### Build

```bash  
mvn clean install
```  

### Run

```bash  
mvn spring-boot:run
```  
  
---  

## PostgreSQL Docker Setup

Run container

```bash  
docker run --name healthmaxxing-postgres -e POSTGRES_DB=healthmaxxing_db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres
``` 

---  

## Future Improvements

- Swagger / OpenAPI documentation
- Validation for incoming requests
- Unit & Integration tests
- Health statistics dashboard
- Charts and analytics
- Notifications and reminders
- BMI and calorie calculations
- Sleep quality analysis

---  

## Resume Summary

**Health Tracking Application (In Progress)**

**Tech Stack:** Java, Spring Boot, Spring Security, PostgreSQL, Docker

- Developed a backend REST API for tracking daily health metrics
- Designed RESTful APIs for data collection and retrieval
- Implemented authentication and authorization with Spring Security
- Built a PostgreSQL database schema using Spring Data JPA
- Implemented personalized daily health insights
- Containerized the application using Docker
- Applied layered architecture (Controller → Service → Repository)

---  

## Author
**GitHub:** https://github.com/avenhon
