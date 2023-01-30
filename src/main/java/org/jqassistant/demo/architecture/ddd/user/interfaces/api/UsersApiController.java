package org.jqassistant.demo.architecture.ddd.user.interfaces.api;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.jqassistant.demo.architecture.ddd.user.application.UserApplicationService;
import org.jqassistant.demo.architecture.ddd.user.interfaces.api.v1.users.UsersApi;
import org.jqassistant.demo.architecture.ddd.user.interfaces.api.v1.users.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UsersApiController implements UsersApi {

    private final UserApplicationService userApplicationService;

    private final UsersApiMapper usersApiMapper;

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userApplicationService.findAll()
                .stream()
                .map(usersApiMapper::fromDomain)
                .collect(toList()));
    }

    @Override
    public ResponseEntity<User> createOrUpdateUser(User user) {
        User newUser = usersApiMapper.fromDomain(userApplicationService.save(usersApiMapper.toDomain(user)));
        return ResponseEntity.status(CREATED)
                .body(newUser);
    }

    @Override
    public ResponseEntity<User> getUserById(Long id) {
        return userApplicationService.findById(id)
                .map(user -> ResponseEntity.ok(usersApiMapper.fromDomain(user)))
                .orElse(ResponseEntity.notFound()
                        .build());
    }
}
