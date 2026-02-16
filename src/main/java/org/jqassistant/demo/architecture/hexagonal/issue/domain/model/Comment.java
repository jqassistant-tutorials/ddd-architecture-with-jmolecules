package org.jqassistant.demo.architecture.hexagonal.issue.domain.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {

    String content;

}
