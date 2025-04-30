package org.example.githubdiscordupdater.api.model.util;

import org.example.githubdiscordupdater.api.model.pullrequest.PullRequest;
import org.example.githubdiscordupdater.api.model.pullrequest.PullRequestEntity;
import org.example.githubdiscordupdater.api.model.pullrequest.PullRequestPayload;

public class Conversions {
    public static PullRequest createPullRequestFromBody(PullRequestPayload payload) {
        PullRequestPayload.PullRequestBody body = payload.getPullRequestBody();

        return new PullRequest(
                body.getId(),
                body.getTitle(),
                body.getBody(),
                body.getUser().getLogin(),
                body.getUser().getUrl(),
                body.getUser().getAvatarUrl(),
                body.getState(),
                body.getHtmlUrl(),
                payload.getReview());
    }

    public static PullRequestEntity createEntityFromPullRequest(PullRequest pullRequest) {
        PullRequestEntity pullRequestEntity = new PullRequestEntity();
        pullRequestEntity.setPullRequestId(pullRequest.getPullRequestId());
        pullRequestEntity.setTitle(pullRequest.getTitle());
        pullRequestEntity.setDescription(pullRequest.getDescription());
        pullRequestEntity.setAuthor(pullRequest.getAuthor());
        pullRequestEntity.setAuthorUrl(pullRequest.getAuthorUrl());
        pullRequestEntity.setAvatarUrl(pullRequest.getAvatarUrl());
        pullRequestEntity.setState(pullRequest.getState());
        pullRequestEntity.setUrl(pullRequest.getUrl());
        return pullRequestEntity;
    }

    public static PullRequest createPullRequestFromEntity(PullRequestEntity pullRequestEntity) {
        return new PullRequest(
                pullRequestEntity.getPullRequestId(),
                pullRequestEntity.getTitle(),
                pullRequestEntity.getDescription(),
                pullRequestEntity.getAuthor(),
                pullRequestEntity.getAuthorUrl(),
                pullRequestEntity.getAvatarUrl(),
                pullRequestEntity.getState(),
                pullRequestEntity.getUrl());
    }
}
