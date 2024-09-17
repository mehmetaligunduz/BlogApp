package com.scalefocus.blogapp;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class BlogAppApplication implements CommandLineRunner {

    private final PostService postService;

    public static void main(String[] args) {
        SpringApplication.run(BlogAppApplication.class, args);
    }

    @Override
    public void run(String... args) {
        List<PostEntity> postEntities = List.of(
                new PostEntity("Understanding REST APIs",
                        "In this post, we explore the basics of REST APIs and how they are used in modern web applications."),
                new PostEntity("Getting Started with Spring Boot",
                        "Spring Boot simplifies Java development by providing a platform for rapid application development."),
                new PostEntity("Top 10 Java Libraries You Should Know",
                        "Discover the top Java libraries that can help improve your productivity and code quality."),
                new PostEntity("Introduction to Microservices Architecture",
                        "Learn about the microservices architecture and how it differs from the traditional monolithic approach."),
                new PostEntity("Effective Debugging Techniques",
                        "In this post, we discuss useful techniques for debugging Java applications more effectively."),
                new PostEntity("Understanding Dependency Injection",
                        "This article explains the concept of dependency injection and how it promotes loose coupling in code."),
                new PostEntity("Best Practices for Writing Clean Code",
                        "Learn the best practices for writing clean, readable, and maintainable Java code."),
                new PostEntity("What is Object-Oriented Programming?",
                        "This post provides an overview of object-oriented programming principles and their importance in Java."),
                new PostEntity("Guide to Unit Testing with JUnit",
                        "A comprehensive guide to writing effective unit tests using JUnit for Java applications."),
                new PostEntity("Exploring Design Patterns in Java",
                        "In this post, we take a deep dive into common design patterns and their implementations in Java.")
        );

        postService.saveAll(postEntities);

    }
}
