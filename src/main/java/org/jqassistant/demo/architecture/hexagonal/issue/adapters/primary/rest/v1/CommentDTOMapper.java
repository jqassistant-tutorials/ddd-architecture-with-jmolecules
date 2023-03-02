package org.jqassistant.demo.architecture.hexagonal.issue.adapters.primary.rest.v1;

import org.jqassistant.demo.architecture.hexagonal.issue.domain.model.Comment;
import org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1.issues.model.CommentDTO;
import org.mapstruct.Mapper;

/**
 * Defines of the mapper between the domain model and the DTOs of the users REST API.
 * <p>
 * The actual implementation is generated using MapStruct.
 */
@Mapper
public interface CommentDTOMapper {

    Comment toDomain(CommentDTO commentDTO);

    CommentDTO fromDomain(Comment comment);

}

