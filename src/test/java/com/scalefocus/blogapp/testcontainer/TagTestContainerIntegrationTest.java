package com.scalefocus.blogapp.testcontainer;

import com.scalefocus.blogapp.model.LoginRequest;
import com.scalefocus.blogapp.model.LoginResponse;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class TagTestContainerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    private String token;

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

        LoginResponse loginResponse = userService.login(new LoginRequest("mgunduz", "123456"));
        token = loginResponse.getToken();

    }


    @Test
    void testAddTagToAPost() throws Exception {

        String requestBody = """
                    {
                         "tags":["IT", "Development", "Java"]
                    }
                """;

        long postId = 2L;

        mockMvc.perform(post("/api/v1/tag-management/posts/" + postId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    void testRemoveTagFromAPost() throws Exception {

        String requestBody = """
                   {
                         "tag": "Java"
                    }
                """;

        long postId = 1L;

        mockMvc.perform(delete("/api/v1/tag-management/posts&" + postId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful());

    }


}
