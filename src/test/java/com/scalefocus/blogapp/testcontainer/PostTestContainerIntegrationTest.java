package com.scalefocus.blogapp.testcontainer;


import com.scalefocus.blogapp.entity.UserEntity;
import com.scalefocus.blogapp.handler.AuthenticationHandler;
import com.scalefocus.blogapp.model.LoginRequest;
import com.scalefocus.blogapp.service.JwtService;
import com.scalefocus.blogapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class PostTestContainerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Container
    private static final MySQLContainer<?> mysqlContainer =
            new MySQLContainer<>("mysql:latest")
                    .withDatabaseName("blogappdb")
                    .withUsername("root")
                    .withPassword("root");

    static {

        mysqlContainer.start();
        System.setProperty("DB_URL", mysqlContainer.getJdbcUrl());
        System.setProperty("DB_USERNAME", mysqlContainer.getUsername());
        System.setProperty("DB_PASSWORD", mysqlContainer.getPassword());

    }


    @BeforeEach
    void login() {

        userService.login(new LoginRequest("mgunduz", "123456"));

    }


    @Test
    void testCreatePost() throws Exception {

        String token = jwtService.generateToken(AuthenticationHandler.getUser());

        String requestBody = """
                    {
                        "title": "Test Post",
                        "text": "This is the content of the test post."
                    }
                """;

        mockMvc.perform(post("/api/v1/posts")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

    }

    @Test
    void testCreatePostWithoutToken() throws Exception {

        String requestBody = """
                    {
                        "title": "Test Post",
                        "text": "This is the content of the test post."
                    }
                """;

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized());

    }

    @Test
    void testGetAllPosts() throws Exception {

        String token = jwtService.generateToken(AuthenticationHandler.getUser());

        mockMvc.perform(get("/api/v1/posts")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty());

    }


    @Test
    void testGetAllPostsByTag() throws Exception {

        String tag = "Java";

        String token = jwtService.generateToken(AuthenticationHandler.getUser());

        mockMvc.perform(get("/api/v1/posts/" + tag)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testUpdatePosts() throws Exception {

        long postId = 1L;

        String token = jwtService.generateToken(AuthenticationHandler.getUser());

        String requestBody = """
                    {
                        "title": "Test Post",
                        "text": "This is the content of the test post."
                    }
                """;

        mockMvc.perform(put("/api/v1/posts/" + postId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

    }

    @Test
    void testDeletePosts() throws Exception {

        Optional<UserEntity> userEntity = userService.findByUsername("mgunduz");

        if (userEntity.isEmpty()) {
            return;
        }

        long postId = 1L;

        String token = jwtService.generateToken(userEntity.get());

        mockMvc.perform(delete("/api/v1/posts/" + postId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void testDeletePostsWithNotCreatorUser() throws Exception {

        long postId = 12L;

        String token = jwtService.generateToken(AuthenticationHandler.getUser());

        mockMvc.perform(delete("/api/v1/posts/" + postId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void testGetAllPostsByUser() throws Exception {

        String token = jwtService.generateToken(AuthenticationHandler.getUser());

        mockMvc.perform(get("/api/v1/posts")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty());

    }

}
