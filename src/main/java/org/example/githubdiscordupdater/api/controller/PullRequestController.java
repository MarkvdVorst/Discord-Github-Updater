package org.example.githubdiscordupdater.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.githubdiscordupdater.api.model.PullRequest.PullRequestPayload;
import org.example.githubdiscordupdater.api.model.PullRequest.PullRequest;
import org.example.githubdiscordupdater.service.PullRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/pull-request")
public class PullRequestController {

    @Autowired
    private PullRequestService pullRequestService;

    @GetMapping
    public List<PullRequest> getAllPullRequests() {
        return pullRequestService.getPullRequests();
    }

    @GetMapping("/{id}")
    public Optional<PullRequest> getPullRequestById(@PathVariable Long id) {
        return pullRequestService.getPullRequestById(id);
    }

    @PostMapping
    public ResponseEntity<String> savePullRequest(@RequestBody PullRequestPayload payload) {
        PullRequest pullRequest = createPullRequestFromBody(payload);
        pullRequestService.savePullRequest(pullRequest);
        return ResponseEntity.ok(pullRequest.getPullRequestId() + " " + pullRequest.getTitle() + " " + pullRequest.getDescription() + " " + pullRequest.getAuthor() + " " + pullRequest.getState() + " " + pullRequest.getUrl());
    }

    @DeleteMapping("/{id}")
    public void deletePullRequest(@PathVariable Long id) {
        pullRequestService.deletePullRequest(id);
    }

    private static PullRequest createPullRequestFromBody(PullRequestPayload payload) {
        PullRequestPayload.PullRequestBody body = payload.getPullRequestBody();

        return new PullRequest(
                body.getId(),
                body.getTitle(),
                body.getBody(),
                body.getUser().getLogin(),
                body.getUser().getUrl(),
                body.getUser().getAvatarUrl(),
                body.getState(),
                body.getUrl());
    }
}
