package org.jqassistant.demo.architecture.ddd.user.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String email;

    private String firstName;

    private String lastName;

}
