package com.example.graphql.client;

import com.example.graphql.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient {

    private final RestTemplate restTemplate;

    public UserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public User fetchUser(String id) {
        return restTemplate.getForObject("https://jsonplaceholder.typicode.com/users/" + id, User.class);
    }
}
