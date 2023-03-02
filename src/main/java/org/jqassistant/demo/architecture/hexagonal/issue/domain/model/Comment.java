package org.jqassistant.demo.architecture.hexagonal.issue.domain.model;

import javax.persistence.Embeddable;

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
