package org.example.githubdiscordupdater.api.model.PullRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PullRequestPayload {
    @Getter
    @Setter
    @JsonProperty("pull_request")
    private PullRequestBody pullRequestBody;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class PullRequestBody {
        private long id;
        private String title;
        private String body;
        private User user;
        private String state;
        @JsonProperty("html_url")
        private String htmlUrl;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class User {
        private String login;
        @JsonProperty("avatar_url")
        private String avatarUrl;
        private String url;
    }
}
