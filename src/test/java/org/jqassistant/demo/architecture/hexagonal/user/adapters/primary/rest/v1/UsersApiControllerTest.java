package org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1;

import org.jqassistant.demo.architecture.hexagonal.shared.domain.exception.NotFoundException;
import org.jqassistant.demo.architecture.hexagonal.user.application.UserApplicationService;
import org.jqassistant.demo.architecture.hexagonal.user.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersApiControllerTest {

    public static final String REST_V1_USERS = "/rest/v1/users";

    @MockBean
    private UserApplicationService userApplicationService;

    @Autowired
    private MockMvc mockMvc;

    private Map<Long, User> users;

    @BeforeEach
    void stub() {
        users = new LinkedHashMap<>();
        doAnswer(invocation -> {
            User user = invocation.getArgument(0);
            long id = users.size();
            User newUser = user.toBuilder()
                .id(id)
                .build();
            users.put(id, newUser);
            return newUser;
        }).when(userApplicationService)
            .create(any(User.class));

        doAnswer(invocation -> users.values()
            .stream()
            .collect(toList())).when(userApplicationService)
            .findAll();

        doAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            User user = users.get(id);
            if (user == null) {
                throw new NotFoundException("User not found");
            }
            return user;
        }).when(userApplicationService)
            .findById(any(long.class));
    }

    @Test
    void createUser() throws Exception {
        // when
        this.mockMvc.perform(post(REST_V1_USERS).contentType(APPLICATION_JSON)
                .content("{ \"email\": \"foo.bar@example.com\", \"firstName\": \"Foo\", \"lastName\": \"Bar\" }")
                .accept(APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("id", equalTo(0L), Long.class))
            .andExpect(jsonPath("email", equalTo("foo.bar@example.com")))
            .andExpect(jsonPath("firstName", equalTo("Foo")))
            .andExpect(jsonPath("lastName", equalTo("Bar")));

        // then
        verify(userApplicationService).create(any(User.class));
        User user = users.get(0L);
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("foo.bar@example.com");
        assertThat(user.getFirstName()).isEqualTo("Foo");
        assertThat(user.getLastName()).isEqualTo("Bar");
    }

    @Test
    void getUsers() throws Exception {
        // given
        prepareUser();

        // when
        this.mockMvc.perform(get(REST_V1_USERS).accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)));

        // then
        verify(userApplicationService).findAll();
    }

    @Test
    void getUser() throws Exception {
        // given
        User user = prepareUser();

        // when
        this.mockMvc.perform(get(REST_V1_USERS + "/" + user.getId()).accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("id", equalTo(0)))
            .andExpect(jsonPath("firstName", equalTo("Foo")))
            .andExpect(jsonPath("lastName", equalTo("Bar")))
            .andExpect(jsonPath("email", equalTo("foo.bar@example.com")));

        // then
        verify(userApplicationService).findById(0);
    }

    @Test
    void getNonExistingUser() throws Exception {
        this.mockMvc.perform(get(REST_V1_USERS + "/0").accept(APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    private User prepareUser() {
        long id = users.size();
        User user = User.builder()
            .id(id)
            .email("foo.bar@example.com")
            .firstName("Foo")
            .lastName("Bar")
            .build();
        users.put(id, user);
        return user;
    }
}
