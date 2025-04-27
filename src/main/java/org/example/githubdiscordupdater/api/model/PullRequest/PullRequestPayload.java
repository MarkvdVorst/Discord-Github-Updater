package org.example.githubdiscordupdater.api.model.PullRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class PullRequestPayload {
    @Getter
    @Setter
    @JsonProperty("pull_request")
    private PullRequestBody pullRequestBody;

    @Getter
    @Setter
    public static class PullRequestBody {
        private long id;
        private String title;
        private String body;
        private User user;
        private String state;
        private String url;
    }

    @Getter
    @Setter
    public static class User {
        private String login;
        @JsonProperty("avatar_url")
        private String avatarUrl;
        private String url;
    }
}
