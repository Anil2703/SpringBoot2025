# SpringBoot2025

A Spring Boot 3.x application with OpenAPI/Swagger documentation and auto-generated models from OpenAPI specifications.

## Overview

This is a Car Inventory API with integrated OpenAPI 3.0 specification and Swagger UI for interactive API documentation. The Car data model is automatically generated from the OpenAPI specification during the build process.

## Database

Neon (PostgreSQL):  
https://console.neon.tech/app/projects

## Project Structure

```
SpringBoot2025/
├── src/
│   ├── main/
│   │   ├── java/com/rocky/
│   │   │   ├── Application.java          # Main entry point
│   │   │   ├── controller/
│   │   │   │   └── Controller.java       # REST endpoints
│   │   │   ├── service/
│   │   │   │   └── CarsService.java      # Business logic
│   │   │   ├── model/
│   │   │   │   └── (Car.java auto-generated)
│   │   │   ├── dao/
│   │   │   ├── config/
│   │   │   └── exception/
│   │   └── resources/
│   │       ├── application.yml           # Spring Boot config
│   │       ├── openapi.yaml              # OpenAPI specification
│   │       ├── schema.sql
│   │       └── data.sql
│   └── test/
├── target/                               # (Git ignored - build artifacts)
├── pom.xml                               # Maven configuration
├── README.md                             # This file
└── .gitignore                            # Git ignore rules
```

## Build & Run

### Prerequisites

- Java 17+
- Maven 3.6+

### Build

```bash
mvn clean package
```

This will:
1. Generate Car model from `src/main/resources/openapi.yaml`
2. Compile all source code
3. Run tests
4. Package as JAR: `target/SpringBoot2025-1.0-SNAPSHOT.jar`

### Run

```bash
java -jar target/SpringBoot2025-1.0-SNAPSHOT.jar
```

Or run `Application.class` from your IDE.

## API Documentation

Once running, access the API documentation:

| URL | Purpose |
|-----|---------|
| http://localhost:8080/carsapp/swagger-ui.html | Interactive Swagger UI |
| http://localhost:8080/carsapp/v3/api-docs | OpenAPI JSON specification |
| http://localhost:8080/carsapp/v3/api-docs.yaml | OpenAPI YAML specification |

## API Endpoints

- `GET /defaultMessage` - Greeting message
- `GET /totalBusinessOrders` - Total cars count
- `GET /getAllCars` - Retrieve all cars
- `GET /getCarByModel/{model}` - Get car by model name
- `POST /addCarToInventory` - Add new car to inventory

## ⚠️ Git & Build Artifacts

### NEVER Commit `target/` Directory

The `.gitignore` file excludes the following from version control:

- **`target/`** - Maven build output (includes compiled classes, generated models, JAR files)
- **`.idea/`** - IntelliJ IDE configuration
- **`*.jar`** - Compiled JAR files
- **`*.log`** - Application logs
- **`*.class`** - Compiled Java classes

**Your Git changes panel should ONLY show source files (`.java`, `.yaml`, `.yml`, `pom.xml`).**

### Cleaning Build Artifacts

```bash
mvn clean
```

Or manually:

```powershell
Remove-Item -Recurse -Force target
```

## OpenAPI Code Generation

The Car model is automatically generated from `src/main/resources/openapi.yaml` during Maven build.

**To modify the Car model:**
1. Edit `src/main/resources/openapi.yaml`
2. Run `mvn clean package`
3. Rebuilt JAR includes updated model

## Dependencies

- Spring Boot 3.3.3
- springdoc-openapi-starter-webmvc-ui 2.2.0
- openapi-generator-maven-plugin 7.1.0
- jackson-databind-nullable 0.2.4
- PostgreSQL driver
- Bucket4J (rate limiting)

