package org.jqassistant.demo.architecture.ddd.user.infrastructure.repository;

import javax.persistence.EntityManager;

import org.jmolecules.architecture.hexagonal.SecondaryAdapter;
import org.jqassistant.demo.architecture.ddd.user.domain.UserRepository;
import org.jqassistant.demo.architecture.ddd.user.domain.model.User;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

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
