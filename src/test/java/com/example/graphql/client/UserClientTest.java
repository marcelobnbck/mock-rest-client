package com.example.graphql.client;

import com.example.graphql.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(UserClient.class)
class UserClientTest {

    @Autowired
    private UserClient userClient;

    @Autowired
    private MockRestServiceServer mockServer;

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
