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
public class Issue {

    @Id
    @GeneratedValue
    private Long id;

    private IssueType type;

    private String title;

    private String description;

    @ManyToOne
    private User assignee;

    @ElementCollection
    @OrderColumn
    private List<Comment> comments;

}
