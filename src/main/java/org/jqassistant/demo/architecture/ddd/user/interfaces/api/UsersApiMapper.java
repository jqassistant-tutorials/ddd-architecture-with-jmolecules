package org.jqassistant.demo.architecture.ddd.user.interfaces.api;

import org.jqassistant.demo.architecture.ddd.user.domain.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UsersApiMapper {

    User toDomain(org.jqassistant.demo.architecture.ddd.user.interfaces.api.v1.users.model.User user);

    org.jqassistant.demo.architecture.ddd.user.interfaces.api.v1.users.model.User fromDomain(User user);

}
