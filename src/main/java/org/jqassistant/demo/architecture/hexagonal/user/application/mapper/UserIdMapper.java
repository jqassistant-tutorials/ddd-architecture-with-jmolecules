package org.jqassistant.demo.architecture.hexagonal.user.application.mapper;

import org.jqassistant.demo.architecture.hexagonal.user.application.UserApplicationService;
import org.jqassistant.demo.architecture.hexagonal.user.domain.model.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

/**
 * Defines the mapper for {@link User}s and their Ids.
 * <p>
 * The actual implementation is generated using MapStruct.
 */
@Mapper
public interface UserIdMapper {

    default Long map(User user) {
        return user != null ? user.getId() : null;
    }

    default User map(Long id, @Context UserApplicationService userApplicationService) {
        return userApplicationService.findById(id);
    }
}

