package org.example.githubdiscordupdater.api.model.PullRequest;

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
}
