package org.jqassistant.demo.architecture.ddd.user.application;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.jqassistant.demo.architecture.ddd.user.domain.UserDomainService;
import org.jqassistant.demo.architecture.ddd.user.domain.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@PrimaryPort
@Service
@Slf4j
@RequiredArgsConstructor
public class UserApplicationService {

    private final UserDomainService userDomainService;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDomainService.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(long id) {
        return userDomainService.findById(id);
    }

    @Transactional
    public User save(User user) {
        log.info("Saving user {}.", user);
        return userDomainService.save(user);
    }

    @Transactional
    public void delete(long id) {
        log.info("Deleting user with id {}.", id);
        userDomainService.findById(id)
                .ifPresent(userDomainService::delete);
    }
}
