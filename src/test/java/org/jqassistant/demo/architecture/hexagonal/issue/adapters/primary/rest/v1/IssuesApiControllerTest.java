package org.jqassistant.demo.architecture.hexagonal.issue.adapters.primary.rest.v1;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jqassistant.demo.architecture.hexagonal.issue.application.IssueApplicationService;
import org.jqassistant.demo.architecture.hexagonal.issue.domain.model.Comment;
import org.jqassistant.demo.architecture.hexagonal.issue.domain.model.Issue;
import org.jqassistant.demo.architecture.hexagonal.shared.domain.exception.NotFoundException;
import org.jqassistant.demo.architecture.hexagonal.user.application.UserApplicationService;
import org.jqassistant.demo.architecture.hexagonal.user.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.jqassistant.demo.architecture.hexagonal.issue.domain.model.IssueType.BUG;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class IssuesApiControllerTest {

    public static final String REST_V1_ISSUES = "/rest/v1/issues";

    @MockBean
    private IssueApplicationService issueApplicationService;

    @MockBean
    private UserApplicationService userApplicationService;

    @Autowired
    private MockMvc mockMvc;

    private Map<Long, Issue> issues;

    private Map<Long, User> users;

    @BeforeEach
    void stub() {
        issues = new LinkedHashMap<>();
        users = new LinkedHashMap<>();

        // Create issue
        doAnswer(invocation -> {
            Issue issue = invocation.getArgument(0);
            long id = issues.size();
            Issue newIssue = issue.toBuilder()
                    .id(id)
                    .build();
            issues.put(newIssue.getId(), newIssue);
            return newIssue;
        }).when(issueApplicationService)
                .create(any(Issue.class));

        // Find issue
        doAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            Issue issue = issues.get(id);
            if (issue == null) {
                throw new NotFoundException("Issue not found.");
            }
            return issue;
        }).when(issueApplicationService)
                .findById(anyLong());

        // Assign issue
        doAnswer(invocation -> {
            Issue issue = invocation.getArgument(0);
            Long assigneeId = invocation.getArgument(1);
            User assignee = users.get(assigneeId);
            Issue updatedIssue = issue.toBuilder()
                    .assignee(assignee)
                    .build();
            issues.put(updatedIssue.getId(), updatedIssue);
            return updatedIssue;
        }).when(issueApplicationService)
                .assignUser(any(), anyLong());

        // Comment issue
        doAnswer(invocation -> {
            Issue issue = invocation.getArgument(0);
            Comment comment = invocation.getArgument(1);
            Issue updatedIssue = issue.toBuilder()
                    .comment(comment)
                    .build();
            issues.put(updatedIssue.getId(), updatedIssue);
            return updatedIssue;
        }).when(issueApplicationService)
                .comment(any(), any());
    }

    @Test
    void createIssue() throws Exception {
        // when
        this.mockMvc.perform(post(REST_V1_ISSUES).contentType(APPLICATION_JSON)
                        .content("{ \"type\": \"BUG\", \"title\": \"bug title\", \"description\": \"bug description\" }")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated());

        // then
        verify(issueApplicationService).create(any(Issue.class));

        Issue issue = issues.get(0l);
        assertThat(issue).isNotNull();
        assertThat(issue.getId()).isEqualTo(0l);
        assertThat(issue.getType()).isEqualTo(BUG);
        assertThat(issue.getTitle()).isEqualTo("bug title");
        assertThat(issue.getDescription()).isEqualTo("bug description");
    }

    @Test
    void getIssue() throws Exception {
        // given
        Issue issue = storeIssue();

        // when
        this.mockMvc.perform(get(REST_V1_ISSUES + "/" + issue.getId()).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("id", equalTo(issue.getId()), Long.class))
                .andExpect(jsonPath("type", equalTo("BUG")))
                .andExpect(jsonPath("title", equalTo("bug title")))
                .andExpect(jsonPath("description", equalTo("bug description")));

        // then
        verify(issueApplicationService).findById(issue.getId());
    }

    @Test
    void getNonExistingIssue() throws Exception {
        // when
        this.mockMvc.perform(get(REST_V1_ISSUES + "/0").accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void assignIssue() throws Exception {
        // given
        Issue issue = storeIssue();
        User assignee = storeUser();
        doReturn(Optional.of(assignee)).when(userApplicationService)
                .findById(assignee.getId());

        // when
        this.mockMvc.perform(put(REST_V1_ISSUES + "/" + issue.getId() + "/assignee").contentType(APPLICATION_JSON)
                        .content("{ \"assigneeId\": " + assignee.getId() + " }")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("id", equalTo(issue.getId()), Long.class))
                .andExpect(jsonPath("assigneeId", equalTo(assignee.getId()), Long.class));

        // then
        verify(issueApplicationService).assignUser(issue, assignee.getId());
        assertThat(issues.get(issue.getId())
                .getAssignee()).isEqualTo(assignee);
    }

    @Test
    void commentIssue() throws Exception {
        // given
        Issue issue = storeIssue();

        // when
        this.mockMvc.perform(post(REST_V1_ISSUES + "/" + issue.getId() + "/comments").contentType(APPLICATION_JSON)
                        .content("{ \"content\": \"comment content\" }")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("id", equalTo(issue.getId()), Long.class))
                .andExpect(jsonPath("comments", hasSize(1)));

        // then
        verify(issueApplicationService).comment(eq(issue), any(Comment.class));
        List<Comment> comments = issues.get(issue.getId())
                .getComments();
        assertThat(comments).isNotEmpty()
                .hasSize(1);
        Comment comment = comments.get(0);
        assertThat(comment.getContent()).isEqualTo("comment content");
    }

    private Issue storeIssue() {
        long id = issues.size();
        Issue issue = Issue.builder()
                .id(id)
                .type(BUG)
                .title("bug title")
                .description("bug description")
                .build();
        issues.put(id, issue);
        return issue;
    }

    private User storeUser() {
        long id = users.size();
        User user = User.builder()
                .id(id)
                .build();
        users.put(id, user);
        return user;
    }
}
