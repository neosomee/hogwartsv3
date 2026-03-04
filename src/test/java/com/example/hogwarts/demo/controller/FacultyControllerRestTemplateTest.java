package com.example.hogwarts.demo.controller;

import com.example.hogwarts.demo.model.Faculty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FacultyControllerRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void contextLoads() {
        assertThat(restTemplate).isNotNull();
    }

    @Test
    void testDefaultMessage() {
        String result = restTemplate.getForObject(
                url("/"),
                String.class
        );
        assertThat(result).isNotNull();
    }

    @Test
    void testGetFaculties() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                url("/faculties"),
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testPostFaculty() {

        Faculty faculty = new Faculty();
        faculty.setName("Hogwarts_" + System.currentTimeMillis());
        faculty.setColor("Green");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Faculty> request = new HttpEntity<>(faculty, headers);

        ResponseEntity<Faculty> response = restTemplate.postForEntity(
                url("/faculties"),
                request,
                Faculty.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}