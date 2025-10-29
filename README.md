# Weather Forecast & Text Replacement API

A Spring Boot application providing weather forecast data and text replacement functionality with a React TypeScript frontend.

## Overview

This project consists of two main features:
- **Weather Forecast API**: Retrieves weather forecast data with customizable parameters (temperature, humidity, wind speed)
- **Text Replacement API**: Processes text replacement operations

## API Reference

### Weather Forecast

#### Get Forecast

```http
POST /api/v1/forecasts
```

**Request Body:**

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `addTemprature` | `boolean` | **Required**. Include temperature in forecast |
| `addHumidity` | `boolean` | **Required**. Include humidity in forecast |
| `addWindSpeed` | `boolean` | **Required**. Include wind speed in forecast |

**Response:**

```json
{
  "date": "2025-10-29",
  "maxTemperature": 25.5,
  "maxHumidity": 65.0,
  "maxWindSpeed": 15.3
}
```

### Text Replacement

#### Replace Text

```http
GET /api/v1/texts/replace?text={text}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `text` | `string` | **Required**. Text to be processed |

**Response:**

```json
{
  "originalText": "original text",
  "replacedText": "modified text"
}
```

## Tech Stack

**Backend:**
- Spring Boot (Java 17)
- Spring Data JPA
- Spring Validation
- Lombok
- PostgreSQL

**Testing:**
- JUnit 5
- Mockito
- Jacoco (70% code coverage threshold)
- Current Coverage: **72%** (Services, Controllers, and Client packages: **100%**)

**Frontend:**
- React
- TypeScript
- Material-UI (MUI)

**DevOps:**
- Docker & Docker Compose
- Jenkins
- PostgreSQL Database

## Prerequisites

- Docker and Docker Compose
- Java 17 (for local development)
- Maven (for local development)
- Node.js (for local frontend development)

## Deployment

The project uses a Makefile to simplify Docker operations. Below are the available commands:

### Build and Run All Services

```bash
make run
```

This command builds and starts all services (backend, frontend, database, and Jenkins) in detached mode.

### Stop All Services

```bash
make down
```

Stops and removes all running containers.

### Run Tests

```bash
make test
```

Runs the backend tests with Maven. This includes:
- Unit tests
- Integration tests
- Jacoco code coverage report

**Note:** The build will fail if code coverage drops below 70%.

You can view the Jacoco coverage report at:
```
assignment-backend/target/site/jacoco/index.html
```

### Jenkins Operations

#### Build Jenkins Image

```bash
make build-jenkins
```

Builds the Jenkins Docker image.

#### Run Jenkins

```bash
make run-jenkins
```

Starts the Jenkins server in detached mode.

#### Stop Jenkins

```bash
make stop-jenkins
```

Stops the Jenkins container without removing it.

## Environment Configuration

The project uses a `.env` file for environment variables:

```env
DB_HOST=db
DB_PORT=5432
DB_NAME=assignment
DB_USER=postgres
DB_PASSWORD=postgres
```

## Ports

| Service | Port |
| :------ | :--- |
| Backend API | 8080 |
| Frontend | 3000 |
| PostgreSQL | 5432 |
| Jenkins | 8081 |

## Project Structure

```
.
├── assignment-backend/      # Spring Boot backend
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── assignment-frontend/     # React TypeScript frontend
│   ├── src/
│   └── Dockerfile
├── docker-compose.yml
├── Dockerfile              # Jenkins Dockerfile
├── Makefile
└── README.md
```

## Code Coverage & CI/CD

### Jacoco Coverage

The project enforces a minimum of **70% code coverage** using the Jacoco Maven plugin. The current coverage stands at **72%** with:
- Services: 100%
- Controllers: 100%
- Client packages: 100%

### Jenkins Pipeline

A Jenkins server is included in the Docker Compose setup for CI/CD automation. Access Jenkins at `http://localhost:8081`.

## Screenshots

### Jacoco Coverage Reports

#### Successful Build (70%+ Coverage)
*[jacoco_success.png](assets/jacoco_success.png)*

#### Failed Build (Below 70% Coverage)
*[jenkins_failure.png](assets/jenkins_failure.png)*

### Jenkins Pipeline

#### Jenkins Home
*[jenkins_home.png](assets/jenkins_home.png)*

#### Successful Build
*[jenkins_success.png](assets/jenkins_success.png)*

#### Failed Build
*![jenkins_failure.png](assets/jenkins_failure.png)*

## How to run the project

1. Clone the repository
2. Ensure Docker and Docker Compose are installed
3. Configure environment variables in `.env` file in the project root directory:
```env
   DB_HOST=db
   DB_PORT=5432
   DB_NAME=assignment
   DB_USER=postgres
   DB_PASSWORD=postgres
```
You can modify these values based on your requirements.
4. Run the application:
   ```bash
   make run
   ```
5. Access the frontend at `http://localhost:3000`
6. Access the backend API at `http://localhost:8080`
7. Access Jenkins at `http://localhost:8081`

## Testing

Run all tests with coverage:
```bash
make test
```

The test suite includes:
- Unit tests for services
- Controller integration tests
- 100% coverage on critical packages

## Support

For support, email ahmedennaime20@gmail.com

---

**Note:** Make sure to create the `.env` file in the project root directory before running `make run`.