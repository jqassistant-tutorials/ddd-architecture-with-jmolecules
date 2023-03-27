package org.jqassistant.demo.architecture.hexagonal.issue.adapters.primary.rest.v1;

import org.jqassistant.demo.architecture.hexagonal.issue.adapters.primary.rest.v1.issues.model.IssueDTO;
import org.jqassistant.demo.architecture.hexagonal.issue.domain.model.Issue;
import org.jqassistant.demo.architecture.hexagonal.user.application.mapper.UserIdMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Defines of the mapper between the domain model and the DTOs of the users REST API.
 * <p>
 * The actual implementation is generated using MapStruct.
 */
@Mapper(uses = { UserIdMapper.class, CommentDTOMapper.class })
public interface IssueDTOMapper {

    @Mapping(source = "assigneeId", target = "assignee")
    Issue toDomain(IssueDTO issueDTO);

    @InheritInverseConfiguration
    IssueDTO fromDomain(Issue issue);

}

