package org.jqassistant.demo.architecture.ddd.user.interfaces.users;

import org.jqassistant.demo.architecture.ddd.user.domain.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User toDomain(org.jqassistant.demo.architecture.ddd.user.interfaces.users.model.User user);

    org.jqassistant.demo.architecture.ddd.user.interfaces.users.model.User fromDomain(User user);

}
