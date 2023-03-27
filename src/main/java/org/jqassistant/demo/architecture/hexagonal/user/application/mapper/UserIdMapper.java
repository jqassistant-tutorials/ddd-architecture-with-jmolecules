package org.jqassistant.demo.architecture.hexagonal.user.application.mapper;

import org.jqassistant.demo.architecture.hexagonal.user.application.UserApplicationService;
import org.jqassistant.demo.architecture.hexagonal.user.domain.model.User;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Defines the mapper for {@link User}s and their Ids.
 * <p>
 * The actual implementation is generated using MapStruct.
 */
@Mapper
public abstract class UserIdMapper {

    @Autowired
    private UserApplicationService userApplicationService;

    public Long map(User user) {
        return user != null ? user.getId() : null;
    }

    public User map(Long id) {
        return userApplicationService.findById(id);
    }
}

