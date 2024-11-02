
# Trivia

![License](https://img.shields.io/badge/License-MIT-blue.svg)
![Java](https://img.shields.io/badge/Java-17-brightgreen.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.3-brightgreen.svg)
![MongoDB](https://img.shields.io/badge/Database-MongoDB-brightgreen.svg)
![Postgres](https://img.shields.io/badge/Database-Postgres-brightgreen.svg)
![Swagger](https://img.shields.io/badge/Swagger-API%20Docs-brightgreen.svg)

**Trivia** is a Spring Boot application designed to manage trivia questions across various programming languages. It integrates MongoDB for data storage and exposes a RESTful API to manage questions. Additionally, it leverages Swagger to document the API and provide an interactive UI for testing the endpoints.

## Features

- **Question Management**: Add, update, and delete trivia questions for multiple programming languages.
- **Report Management**: Mark trivia questions as resolved or unresolved based on reports.
- **Difficulty Levels**: Categorize questions by difficulty (Easy, Medium, Hard).
- **Random Selection**: Fetch random questions based on difficulty.
- **Swagger Integration**: Provides API documentation and an interactive interface for testing.
- **Exception Handling**: Custom error handling for invalid inputs and rate-limiting.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Swagger Documentation](#swagger-documentation)
- [Contributing](#contributing)
- [License](#license)

## Technologies Used

- **Java 17**
- **Spring Boot 3.3.3**
- **MongoDB** for the database.
- **Postgres** for second database.
- **Swagger** for API documentation.
- **Lombok** for reducing boilerplate code.
- **Maven** for project management and build automation.
- **Jackson** for JSON processing.

## Getting Started

To get a copy of the project running on your local machine, follow the steps below.

### Prerequisites

- **Java 17** or higher.
- **Maven** for building the project.
- **MongoDB** for the database.

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/Skill-Forge-Project/skill_forge_trivia.git
   cd skill_forge_trivia
   ```

2. Install dependencies:

   ```bash
   mvn clean install
   ```

3. Set up MongoDB:

   Ensure MongoDB is running locally, or update the connection string in `application.properties` or `application.yml` with your MongoDB instance details.

4. Create application-env.properties in main directory with the Postgres username and password. 
### Running the Application

You can run the application using the following methods:

- **Using Maven**:

  ```bash
  mvn spring-boot:run
  ```

- **Using the JAR file**:

  ```bash
  java -jar target/Trivia-0.0.1-SNAPSHOT.jar
  ```

## API Endpoints

Here are some key API endpoints:

- **Get Random Questions**:

  ```http
  GET /trivia/{language}/{difficulty}
  ```

- **Add a New Question**:

  ```http
  POST /trivia/questions/{language}/{difficulty}
  ```

- **Update a Question**:

  ```http
  PUT /trivia/questions/{language}/{id}
  ```

- **Delete a Question**:

  ```http
  DELETE /trivia/questions/{language}/{id}
  ```

- **Mark a Reported Question as Resolved**:

  ```http
  PUT /trivia/reports/{id}/resolve
  ```

Replace `{language}` with `java`, `csharp`, `javascript`, or `python`. Replace `{difficulty}` with `Easy`, `Medium`, or `Hard`.

## Swagger Documentation

Swagger provides an interactive interface for testing the API. Once the application is running, visit:

```
http://localhost:8080/swagger-ui.html
```

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit your changes and push to your fork.
4. Submit a pull request with a detailed description of your changes.

### License

This project is licensed under the MIT License. See the [LICENSE](https://github.com/Skill-Forge-Project/skill_forge_trivia/blob/master/LICENSE) file for more details.
