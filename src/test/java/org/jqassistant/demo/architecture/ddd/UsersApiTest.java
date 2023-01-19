package org.jqassistant.demo.architecture.ddd;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jqassistant.demo.architecture.ddd.user.interfaces.rest.UsersApiDelegate;
import org.jqassistant.demo.architecture.ddd.user.interfaces.rest.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class UsersApiTest {

    @MockBean
    private UsersApiDelegate usersApiDelegate;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createAndFindUser() throws Exception {
        Map<String, User> users = new LinkedHashMap<>();
        doAnswer(invocation -> {
            User user = invocation.getArgument(0, User.class);
            users.put(user.getEmail(), user);
            return ResponseEntity.status(CREATED)
                    .build();
        }).when(usersApiDelegate)
                .createOrUpdateUser(any(User.class));

        doAnswer(invocation -> ResponseEntity.ok(users.values()
                .stream()
                .collect(toList()))).when(usersApiDelegate)
                .getAllUsers();

        this.mockMvc.perform(put("/users").contentType(APPLICATION_JSON)
                        .content("{ \"email\": \"dirk.mahler@buschmais.com\", \"firstName\": \"Dirk\", \"lastName\": \"Mahler\" }")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/users").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

}
