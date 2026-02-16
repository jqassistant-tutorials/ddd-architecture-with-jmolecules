package org.jqassistant.demo.architecture.hexagonal.issue.domain.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jqassistant.demo.architecture.hexagonal.user.domain.model.User;

@AggregateRoot
@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
