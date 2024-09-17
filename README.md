**BlogApp** is a Java-based API designed to manage and interact with blog posts. Built using modern technologies, this application allows users to create, update, and view blog posts. It showcases key concepts in RESTful API development and Java Spring Boot.

## Features

- **Post Management**: Create, update, and view blog posts.
- **Tagging System**: Add and remove tags for categorizing posts.
- **Post Filtering**: Retrieve posts by tag**

## Technologies Used

- **Java 21**: The core programming language.
- **Spring Boot**: Framework for building RESTful APIs and managing application logic.
- **H2**: Database system for data storage.
- **JPA/Hibernate**: ORM for database interactions.
- **Gradle**: Dependency management and build tool.

## Getting Started

### Prerequisites

- **Java 17** or higher
- **Gradle**
- **H2** database

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/mehmetaligunduz/BlogApp.git
   cd BlogApp
2. Configure your database connection in src/main/resources/application.yaml:
  spring:
    datasource:
      url: jdbc:h2:mem:blogappdb
      driver-class-name: org.h2.Driver
      username: sa
      password:
    h2:
      console:
        enabled: true
    jpa:
      hibernate:
        ddl-auto: update
3. 
