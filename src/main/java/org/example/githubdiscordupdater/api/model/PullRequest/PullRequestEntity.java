package org.example.githubdiscordupdater.api.model.PullRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class PullRequestEntity {
    @Id
    private Long pullRequestId;
    private String title;
    private String description;
    private String author;
    private String authorUrl;
    private String avatarUrl;
    private String state;
    private String url;

    public PullRequestEntity() {}
}
