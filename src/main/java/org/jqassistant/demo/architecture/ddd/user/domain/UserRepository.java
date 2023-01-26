package org.jqassistant.demo.architecture.ddd.user.domain;

import java.util.List;

import org.jqassistant.demo.architecture.ddd.user.domain.model.User;

public interface UserRepository {

    List<User> findAll();

    User save(User user);
}
