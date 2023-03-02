package org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1.users.UsersApi;
import org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1.users.model.UserDTO;
import org.jqassistant.demo.architecture.hexagonal.user.application.UserApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.*;

/**
 * Implementation of the {@link UsersApi} API using a Spring REST controller.
 */
@PrimaryAdapter
@RestController
@RequestMapping("/rest/v1")
@RequiredArgsConstructor
@Slf4j
public class UsersApiController implements UsersApi {

    private final UserApplicationService userApplicationService;

    private final UserDTOMapper userDTOMapper;

    @Override
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.info("Get all users.");
        return ok(userApplicationService.findAll()
                .stream()
                .map(userDTOMapper::fromDomain)
                .collect(toList()));
    }

    @Override
    public ResponseEntity<UserDTO> createOrUpdateUser(UserDTO userDTO) {
        log.info("Create or update user '{}'.", userDTO);
        return status(CREATED).body(userDTOMapper.fromDomain(userApplicationService.save(userDTOMapper.toDomain(userDTO))));
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(Long id) {
        log.info("Get user by id '{}'.", id);
        return userApplicationService.findById(id)
                .map(user -> ok(userDTOMapper.fromDomain(user)))
                .orElse(notFound().build());
    }
}
