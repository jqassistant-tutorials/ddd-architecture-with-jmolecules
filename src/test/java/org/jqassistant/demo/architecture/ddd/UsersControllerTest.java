package org.jqassistant.demo.architecture.ddd;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jqassistant.demo.architecture.ddd.user.application.UserApplicationService;
import org.jqassistant.demo.architecture.ddd.user.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static java.lang.Long.valueOf;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {

    @MockBean
    private UserApplicationService userApplicationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createAndFindUser() throws Exception {
        Map<Long, User> users = new LinkedHashMap<>();
        doAnswer(invocation -> {
            User user = User.builder()
                    .id(valueOf(users.size()))
                    .email(invocation.getArgument(0))
                    .firstName(invocation.getArgument(1))
                    .lastName(invocation.getArgument(2))
                    .build();
            users.put(user.getId(), user);
            return user;
        }).when(userApplicationService)
                .create(any(String.class), any(String.class), any(String.class));

        doAnswer(invocation -> users.values()
                .stream()
                .collect(toList())).when(userApplicationService)
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
