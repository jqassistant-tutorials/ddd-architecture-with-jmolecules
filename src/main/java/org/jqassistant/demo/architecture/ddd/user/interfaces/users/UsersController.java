package org.jqassistant.demo.architecture.ddd.user.interfaces.users;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.jqassistant.demo.architecture.ddd.user.application.UserApplicationService;
import org.jqassistant.demo.architecture.ddd.user.interfaces.users.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
public class UsersController implements UsersApi {

    private final UserApplicationService userApplicationService;

    private final UserMapper userMapper;

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userApplicationService.getAllUsers()
                .stream()
                .map(user -> userMapper.fromDomain(user))
                .collect(toList()));
    }

    @Override
    public ResponseEntity<User> createOrUpdateUser(User user) {
        User newUser = userMapper.fromDomain(userApplicationService.create(user.getEmail(), user.getFirstName(), user.getLastName()));
        return ResponseEntity.status(CREATED)
                .body(newUser);
    }
}
