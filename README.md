# Trivia

![License](https://img.shields.io/badge/License-MIT-blue.svg)
![Java](https://img.shields.io/badge/Java-17-brightgreen.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.3-brightgreen.svg)
![MongoDB](https://img.shields.io/badge/Database-MongoDB-brightgreen.svg)

Trivia is a Spring Boot application designed to manage trivia questions across various programming languages. It leverages MongoDB as its database and uses Spring Data MongoDB to perform database operations. The application supports questions related to languages such as Java, C#, JavaScript, and Python.

## Features

- **Question Management**: Add, update, and manage trivia questions for multiple programming languages.
- **Difficulty Levels**: Categorize questions by difficulty (Easy, Medium, Hard).
- **Random Selection**: Fetch random questions based on difficulty level.
- **RESTful API**: Expose endpoints to interact with the question bank.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)
- [License](#license)

## Technologies Used

- **Java 17**
- **Spring Boot 3.3.3**
- **MongoDB**
- **Lombok** - For reducing boilerplate code.
- **Jackson** - For JSON processing.
- **Maven** - For project management and build automation.

## Getting Started

To get a copy of the project up and running on your local machine, follow the installation instructions below.

### Prerequisites

- **Java 17** or higher
- **Maven** for building the project
- **MongoDB** for the database

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/Borovaneca/Trivia.git
   cd Trivia
   ```

2. Install dependencies:

   ```bash
   mvn clean install
   ```

3. Set up MongoDB:

   Ensure MongoDB is running on your local machine or update the connection string in \`application.properties\` or \`application.yml\` with your MongoDB instance details.

### Running the Application

You can run the application in several ways:

- **Using Maven**:

  ```bash
  mvn spring-boot:run
  ```

- **Using the JAR file**:

  ```bash
  java -jar target/Trivia-0.0.1-SNAPSHOT.jar
  ```

### API Endpoints

Here are some of the key API endpoints:

- **Get 10 Random Easy Questions** (for a specific language)
  
  ```
  GET /trivia/{language}/{difficulty}
  ```

> **IN PROGRESS**
> 
> **Add a New Question:**
> ```
> POST /trivia/questions/{language}/{difficulty}
> ```
> 
> **Update a Question:**
> ```
> PUT /trivia/questions/{language}/{id}
> ```
> 
> **Delete a Question:**
> ```
> DELETE /trivia/questions/{language}/{id}
> ```
**Note**: Replace \`{language}\` with \`java\`, \`csharp\`, \`javascript\`, or \`python\`.<br>
**Note**: Replace \`{difficulty}\` with \`Easy\`, \`Medium\` or \`Hard\`.

### Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bugfix.
3. Commit your changes and push to your fork.
4. Submit a pull request with a detailed description of your changes.

### License

This project is licensed under the MIT License. See the [LICENSE](https://github.com/git/git-scm.com/blob/main/MIT-LICENSE.txt) file for more details.
