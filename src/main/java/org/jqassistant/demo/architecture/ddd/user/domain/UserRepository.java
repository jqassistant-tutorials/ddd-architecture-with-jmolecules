package org.jqassistant.demo.architecture.ddd.user.domain;

import java.util.List;
import java.util.Optional;

import org.jqassistant.demo.architecture.ddd.user.domain.model.User;

public interface UserRepository {

    Optional<User> findById(Long id);

    List<User> findAll();

    User save(User user);

    void delete(User user);
}
