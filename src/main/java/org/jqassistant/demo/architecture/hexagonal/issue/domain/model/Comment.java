package org.jqassistant.demo.architecture.hexagonal.issue.domain.model;

import javax.persistence.Embeddable;

import lombok.*;

@Embeddable
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    String content;

}
