package org.jqassistant.demo.architecture.hexagonal.issue.domain;

import java.util.List;
import java.util.Optional;

import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.jqassistant.demo.architecture.hexagonal.issue.domain.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Defines the issue repository interface.
 */
@Repository
@SecondaryPort
public interface IssueRepository extends JpaRepository<Issue, Long> {

    Optional<Issue> findById(Long id);

    List<Issue> findAll();

    Issue save(Issue issue);

    void delete(Issue issue);
}
