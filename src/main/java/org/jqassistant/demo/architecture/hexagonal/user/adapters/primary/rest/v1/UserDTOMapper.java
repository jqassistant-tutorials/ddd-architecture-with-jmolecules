package org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1;

import org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1.users.model.UserDTO;
import org.jqassistant.demo.architecture.hexagonal.user.domain.model.User;
import org.mapstruct.Mapper;

/**
 * Defines of the mapper between the domain model and the DTOs of the users REST API.
 * <p>
 * The actual implementation is generated using MapStruct.
 */
@Mapper
public interface UserDTOMapper {

    User toDomain(UserDTO userDTO);

    UserDTO fromDomain(User user);

}
