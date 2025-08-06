package com.example.graphql.client;

import com.example.graphql.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

    @RestClientTest(UserClient.class)
class UserClientTest {

    @Autowired
    private UserClient userClient;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setup() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void shouldFetchUserSuccessfully() {
        String body = """
                    {
                      "id": "1",
                      "name": "Leanne Graham",
                      "email": "leanne@example.com"
                    }
                """;

        mockServer.expect(requestTo("https://jsonplaceholder.typicode.com/users/1"))
                  .andRespond(withSuccess(body, MediaType.APPLICATION_JSON));

        User user = userClient.fetchUser("1");

        assertEquals("Leanne Graham", user.getName());
        assertEquals("leanne@example.com", user.getEmail());

        mockServer.verify();
    }
}
