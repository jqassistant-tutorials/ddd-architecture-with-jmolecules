package org.jqassistant.demo.architecture.hexagonal.issue.domain.model;

import java.util.List;

import javax.persistence.*;

import lombok.*;
import org.jqassistant.demo.architecture.hexagonal.user.domain.model.User;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Issue {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include

    private Long id;

    private IssueType type;

    private String title;

    private String description;

    @ManyToOne
    private User assignee;

    @ElementCollection
    @OrderColumn
    @Singular
    private List<Comment> comments;

}
