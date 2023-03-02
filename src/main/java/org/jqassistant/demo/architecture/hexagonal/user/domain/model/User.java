package org.jqassistant.demo.architecture.hexagonal.user.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.*;

/**
 * The domain entity defining a user.
 * <p>
 * Note: For convenience reasons this class is already annotated as JPA {@link Entity}.
 */
@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    private String email;

    private String firstName;

    private String lastName;

}
