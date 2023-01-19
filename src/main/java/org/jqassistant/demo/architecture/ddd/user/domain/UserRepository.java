package org.jqassistant.demo.architecture.ddd.user.domain;

import org.jqassistant.demo.architecture.ddd.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
