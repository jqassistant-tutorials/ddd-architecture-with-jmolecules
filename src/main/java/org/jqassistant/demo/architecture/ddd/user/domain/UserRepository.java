package org.jqassistant.demo.architecture.ddd.user.domain;

import java.util.List;
import java.util.Optional;

import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.jqassistant.demo.architecture.ddd.user.domain.model.User;

@SecondaryPort
public interface UserRepository {

    Optional<User> findById(Long id);

    List<User> findAll();

    User save(User user);

    void delete(User user);
}
