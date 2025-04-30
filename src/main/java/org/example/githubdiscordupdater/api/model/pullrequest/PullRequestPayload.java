package org.example.githubdiscordupdater.api.model.pullrequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.githubdiscordupdater.api.model.util.ReviewStates;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class PullRequestPayload {

    @JsonIgnoreProperties(ignoreUnknown = true)
    private Review review;

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

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Review {
        private ReviewStates state;
    }
}
