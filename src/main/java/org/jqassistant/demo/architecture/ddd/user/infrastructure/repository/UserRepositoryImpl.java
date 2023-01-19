package org.jqassistant.demo.architecture.ddd.user.infrastructure.repository;

import javax.persistence.EntityManager;

import org.jqassistant.demo.architecture.ddd.user.domain.UserRepository;
import org.jqassistant.demo.architecture.ddd.user.domain.model.User;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class UserRepositoryImpl extends SimpleJpaRepository<User, String> implements UserRepository {

    public UserRepositoryImpl(EntityManager em) {
        super(User.class, em);
    }

}
