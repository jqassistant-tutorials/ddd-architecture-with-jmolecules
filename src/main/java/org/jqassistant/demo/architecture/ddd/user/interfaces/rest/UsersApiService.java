package org.jqassistant.demo.architecture.ddd.user.interfaces.rest;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.jqassistant.demo.architecture.ddd.user.application.UserService;
import org.jqassistant.demo.architecture.ddd.user.interfaces.rest.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@Service
public class UsersApiService implements UsersApiDelegate {

    private final UserService userService;

    private final UserMapper userMapper;

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers()
                .stream()
                .map(user -> userMapper.fromDomain(user))
                .collect(toList()));
    }

    @Override
    public ResponseEntity<Void> createOrUpdateUser(User user) {
        userService.create(user.getEmail(), user.getFirstName(), user.getLastName());
        return ResponseEntity.status(CREATED)
                .build();
    }
}
