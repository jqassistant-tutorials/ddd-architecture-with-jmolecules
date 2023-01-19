package org.jqassistant.demo.architecture.ddd.user.interfaces.rest;

import org.jqassistant.demo.architecture.ddd.user.domain.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User toDomain(org.jqassistant.demo.architecture.ddd.user.interfaces.rest.model.User user);

    org.jqassistant.demo.architecture.ddd.user.interfaces.rest.model.User fromDomain(User user);

}
