package org.jqassistant.demo.architecture.hexagonal.issue.adapters.primary.rest.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.jqassistant.demo.architecture.hexagonal.issue.application.IssueApplicationService;
import org.jqassistant.demo.architecture.hexagonal.issue.domain.model.Issue;
import org.jqassistant.demo.architecture.hexagonal.issue.domain.model.IssueType;
import org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1.issues.IssuesApi;
import org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1.issues.model.AssignIssueRequestDTO;
import org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1.issues.model.CommentDTO;
import org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1.issues.model.CreateIssueRequestDTO;
import org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1.issues.model.IssueDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@PrimaryAdapter
@RestController
@RequestMapping("/rest/v1/")
@RequiredArgsConstructor
@Slf4j
public class IssuesApiController implements IssuesApi {

    private final IssueApplicationService issueApplicationService;

    private final IssueDTOMapper issueDTOMapper;

    private final CommentDTOMapper commentDTOMapper;

    @Override
    public ResponseEntity<IssueDTO> createIssue(CreateIssueRequestDTO createIssueRequest) {
        log.info("Create issue '{}'.", createIssueRequest);
        Issue issue = Issue.builder()
                .title(createIssueRequest.getTitle())
                .description(createIssueRequest.getDescription())
                .type(IssueType.valueOf(createIssueRequest.getType()
                        .name()))
                .build();

        return status(CREATED).body(issueDTOMapper.fromDomain(issueApplicationService.create(issue)));
    }

    @Override
    public ResponseEntity<IssueDTO> getIssue(Long id) {
        log.info("Get issue '{}'.", id);
        return ok(issueDTOMapper.fromDomain(issueApplicationService.findById(id)));
    }

    @Override
    public ResponseEntity<Void> deleteIssue(Long id) {
        log.info("Delete issue '{}'.", id);
        Issue issue = issueApplicationService.findById(id);
        issueApplicationService.delete(issue);
        return ok().build();
    }

    @Override
    public ResponseEntity<IssueDTO> assignIssue(Long id, AssignIssueRequestDTO assignIssueRequestDTO) {
        log.info("Assign issue '{id}' to '{}'.", id, assignIssueRequestDTO);
        Issue issue = issueApplicationService.assignUser(issueApplicationService.findById(id), assignIssueRequestDTO.getAssigneeId());
        return status(CREATED).body(issueDTOMapper.fromDomain(issue));
    }

    @Override
    public ResponseEntity<IssueDTO> commentIssue(Long id, CommentDTO commentDTO) {
        log.info("Comment issue '{id}' with '{}'.", id, commentDTO);
        Issue issue = issueApplicationService.findById(id);
        return status(CREATED).body(issueDTOMapper.fromDomain(issueApplicationService.comment(issue, commentDTOMapper.toDomain(commentDTO))));
    }
}
