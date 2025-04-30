package org.example.githubdiscordupdater.api.model.pullrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PullRequest {
    private Long pullRequestId;
    private String title;
    private String description;
    private String author;
    private String authorUrl;
    private String avatarUrl;
    private String state;
    private String url;
    private PullRequestPayload.Review review;

    public PullRequest(Long pullRequestId, String title, String description, String author, String authorUrl, String avatarUrl, String state, String url) {
        this.pullRequestId = pullRequestId;
        this.title = title;
        this.description = description;
        this.author = author;
        this.authorUrl = authorUrl;
        this.avatarUrl = avatarUrl;
        this.state = state;
        this.url = url;
    }
}
