package org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1.users.UsersApi;
import org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1.users.model.CreateUserRequestDTO;
import org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1.users.model.UserDTO;
import org.jqassistant.demo.architecture.hexagonal.user.application.UserApplicationService;
import org.jqassistant.demo.architecture.hexagonal.user.domain.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

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
    public ResponseEntity<UserDTO> createUser(CreateUserRequestDTO createUserRequestDTO) {
        log.info("Create or update user '{}'.", createUserRequestDTO);
        User user = User.builder().email(createUserRequestDTO.getEmail()).firstName(createUserRequestDTO.getFirstName())
            .lastName(createUserRequestDTO.getLastName())
            .build();
        return status(CREATED).body(userDTOMapper.fromDomain(userApplicationService.create(user)));
    }

    @Override
    public ResponseEntity<List<UserDTO>> getUsers() {
        log.info("Get all users.");
        return ok(userApplicationService.findAll()
            .stream()
            .map(userDTOMapper::fromDomain)
            .collect(toList()));
    }

    @Override
    public ResponseEntity<UserDTO> getUser(Long id) {
        log.info("Get user by id '{}'.", id);
        return ok(userDTOMapper.fromDomain(userApplicationService.findById(id)));
    }

}
