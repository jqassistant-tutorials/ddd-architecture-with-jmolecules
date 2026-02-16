package org.jqassistant.demo.architecture.hexagonal.user.adapters.secondary.database;

import jakarta.persistence.EntityManager;
import org.jmolecules.architecture.hexagonal.SecondaryAdapter;
import org.jqassistant.demo.architecture.hexagonal.user.domain.UserRepository;
import org.jqassistant.demo.architecture.hexagonal.user.domain.model.User;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the {@link UserRepository} defined by the domain layer using a Spring JPA repository.
 */
@SecondaryAdapter
@Repository
public class UserRepositoryImpl extends SimpleJpaRepository<User, Long> implements UserRepository {

    public UserRepositoryImpl(EntityManager em) {
        super(User.class, em);
    }

    @Override
    public User save(User user) {
        return super.save(user);
    }
}
