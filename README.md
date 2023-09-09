# Bookstore Web Application

## Overview

This application is a Java-based web application for managing books in a bookstore. Users can add new books, and the main page displays a list of all books, sorted with the newest at the top. The application ensures that book names are unique and paginates the displayed books.

The application uses Spring Boot, JPA, Thymeleaf, Liquibase, and can switch between H2 or PostgreSQL as a data source.

## Components

### Controllers

1. **BookController** (RESTful Controller)
    - `POST /books`: Creates a new book in the system.
      Example:
      ```json
      {
        "name": "The Great Gatsby",
        "author": "F. Scott Fitzgerald"
      }
      ```
    - `GET /books`: Retrieves a list of all books available in the system.
      Response Example:
      ```json
      [
        {
          "id": 1,
          "name": "The Great Gatsby",
          "author": "F. Scott Fitzgerald",
          "dateAdded": "2023-09-05T10:00:00"
        }
      ]
      ```

2. **BookViewController** (Web MVC Controller)
    - `GET /`: Renders the homepage using Thymeleaf, showing paginated books. Supports pagination via the `pageNo` request parameter.

### Recommendations:

1. **Frontend Framework**: While the current setup uses Thymeleaf for rendering the index page, for a more dynamic and interactive user experience, it would be beneficial to use a frontend framework/application (e.g., React, Vue, Angular) to consume the RESTful API provided by the `BookController`.

2. **Security Implementation**: As per the given requirements, the application does not currently have security measures in place. However, for a production application, it is crucial to incorporate security features. Spring Security can be added to handle authentication and authorization. Specifically:
    - Protect the route used to add books so that only authorized personnel can add new books.
    - Implement user login and registration mechanisms.
    - Use JWT or OAuth2 for API authentication if planning to decouple the frontend from the backend in the future.

### Configuration

`DatabaseConfig`: Configuration for selecting either H2 or PostgreSQL as the data source.


### Data Initialization

`BookDataInitializer`: For testing purposes initializes the database with 100 books if the configuration is enabled.

### Data Transfer Objects (DTOs)

1. `BookCreateRequest`: Represents the data for creating a new book.
2. `BookDTO`: Data representation of a book, including id, name, author, and date added.

### Entity

`Book`: Represents the Book entity with fields like id, name, author, and the date the book was added.

### Configuration Files

1. `application.yaml`: Application-specific configurations such as enabling H2 and Liquibase settings.
2. `docker-compose.yaml`: Used to set up a PostgreSQL database via Docker.
3. `changelog-master.yaml`: Liquibase changelog file for database migrations.

## Setup Guide

### Prerequisites

1. Java (JDK)
2. Maven
3. Docker (if using PostgreSQL from Docker)

### Steps:

1. Clone the repository to your local machine.
2. If using PostgreSQL with Docker:
    - Navigate to the root directory.
    - Run `docker-compose up` to start the PostgreSQL container.
3. Navigate to the root directory of the project using a terminal.
4. Run `mvn clean install` to build the project.
5. Start the application:
    - Using IntelliJ: Import the project and run the main application class.
    - Using Maven: Run `mvn spring-boot:run` in the terminal.
6. Access the web application via `http://localhost:8080/`.
7. H2 console (if H2 is enabled) can be accessed at `http://localhost:8080/h2-console`.