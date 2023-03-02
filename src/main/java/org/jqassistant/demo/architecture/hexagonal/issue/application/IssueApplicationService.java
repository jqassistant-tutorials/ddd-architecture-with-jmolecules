package org.jqassistant.demo.architecture.hexagonal.issue.application;

import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.jqassistant.demo.architecture.hexagonal.issue.domain.IssueRepository;
import org.jqassistant.demo.architecture.hexagonal.issue.domain.model.Comment;
import org.jqassistant.demo.architecture.hexagonal.issue.domain.model.Issue;
import org.jqassistant.demo.architecture.hexagonal.shared.domain.exception.NotFoundException;
import org.jqassistant.demo.architecture.hexagonal.user.application.UserApplicationService;
import org.jqassistant.demo.architecture.hexagonal.user.domain.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@PrimaryPort
@Service
@Transactional
@RequiredArgsConstructor
public class IssueApplicationService {

    private final IssueRepository issueRepository;

    private final UserApplicationService userApplicationService;

    public Issue create(Issue issue) {
        return issueRepository.save(issue);
    }

    public Issue findById(long id) {
        return issueRepository.findById(id)
                .orElseThrow((() -> new NotFoundException("Issue not found.")));
    }

    public Issue assignUser(Issue issue, Long userId) {
        User assignee = userApplicationService.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found."));
        issue.setAssignee(assignee);
        return issueRepository.save(issue);
    }

    public Issue comment(Issue issue, Comment comment) {
        issue.getComments()
                .add(comment);
        return issueRepository.save(issue);
    }

    public void delete(Issue issue) {
        issueRepository.delete(issue);
    }

}
