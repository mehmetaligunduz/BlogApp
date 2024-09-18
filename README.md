## BlogApp 
   It is a Java-based API designed to manage and interact with blog posts. Built using modern technologies, this application allows users to create, update, and view blog posts. It showcases key concepts in RESTful API development and Java Spring Boot.

## Features

- **Post Management**: Create, update, and view blog posts.
- **Tagging System**: Add and remove tags for categorizing posts.
- **Post Filtering**: Retrieve posts by tag

## Technologies Used

- **Java 21**: The core programming language.
- **Spring Boot**: Framework for building RESTful APIs and managing application logic.
- **H2**: Database system for data storage.
- **JPA/Hibernate**: ORM for database interactions.
- **Gradle**: Dependency management and build tool.

## Getting Started

### Prerequisites

- **Java 21**
- **Gradle**
- **H2**

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/mehmetaligunduz/BlogApp.git
   cd BlogApp

2. Configure your database connection in src/main/resources/application.yaml:
   ```bash
   spring:
     application:
       name: BlogApp
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
       defer-datasource-initialization: true
     sql:
       init:
         mode: always
         data-locations: classpath:initial-bootstrap.sql

3. Build the project:
   ```bash
   gradle clean build

4. Run the application:
   ```bash
   gradle bootRun

5. Open your browser and navigate to:
   ```bash
   http://localhost:8080

## API Endpoints

- **```GET /posts```**: Retrieve all blog posts.
- **```POST /posts```**: Create a new blog post.
- **```PUT /posts/{postId}```**: Update an existing post by postId.
- **```GET /posts/{tag}```**: Retrieve posts associated with a specific tag
- **```POST /tag-management/add-tag/posts/{postId}```**: Add tag to post
- **```DELETE /tag-management/delete-tag/{postId}```**: Delete tag from post

### ```GET /posts``` Retrieve all blog posts
      curl --location 'localhost:8080/posts'
      
### ```POST /posts``` Create a new blog post
      curl --location 'localhost:8080/posts' \
      --header 'Content-Type: application/json' \
      --data '{
          "title": "Top 10 Java Libraries You Should Know",
          "text": "Discover the top Java libraries that can help improve your productivity and code quality."
      }'
      
### ```PUT /posts/{postId}``` Update an existing post by postId
      curl --location --request PUT 'localhost:8080/posts/1' \
      --header 'Content-Type: application/json' \
      --data '{
          "title": "Understanding REST APIs",
          "text": "Discover the top Java libraries that can help improve your productivity and code quality."
      }'

### ```GET /posts/{tag}``` Retrieve posts associated with a specific tag
      curl --location 'localhost:8080/posts/development'

### ```POST /tag-management/add-tag/posts/{postId}``` Add tag to post
      curl --location 'localhost:8080/tag-management/add-tag/posts/1' \
      --header 'Content-Type: application/json' \
      --data '{
          "tags":["IT", "Development", "Java"]
      }'

### ```DELETE /tag-management/delete-tag/{postId}``` Delete tag from post
      curl --location --request DELETE 'localhost:8080/tag/1' \
      --header 'Content-Type: application/json' \
      --data '{
          "tag": "IT"
      }'

      
      






