package org.example.githubdiscordupdater.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.githubdiscordupdater.api.SecurityHandler;
import org.example.githubdiscordupdater.api.model.PullRequest.PullRequestPayload;
import org.example.githubdiscordupdater.api.model.PullRequest.PullRequest;
import org.example.githubdiscordupdater.service.PullRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/pull-request")
public class PullRequestController {

    @Autowired
    private PullRequestService pullRequestService;

    @GetMapping
    public ResponseEntity<List<PullRequest>> getAllPullRequests(@RequestBody Map<String, Object> keyValueMap) {
        if (!keyValueMap.containsKey("api_key") || !SecurityHandler.isValidApiKey(keyValueMap.get("api_key").toString())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new LinkedList<>());
        }
        return ResponseEntity.ok(pullRequestService.getPullRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PullRequest>> getPullRequestById(@RequestBody Map<String, Object> keyValueMap) {
        if (!keyValueMap.containsKey("api_key") || !SecurityHandler.isValidApiKey(keyValueMap.get("api_key").toString())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Optional.empty());
        }
        Optional<PullRequest> pullRequest = pullRequestService.getPullRequestById((Long) keyValueMap.get("id"));
        if (pullRequest.isPresent()) {
            return ResponseEntity.ok(pullRequest);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.empty());
        }
    }

    @PostMapping
    public ResponseEntity<String> savePullRequest(@RequestHeader("X-Hub-Signature-256") String signature,
                                                  @RequestBody String rawPayload) {
        try {
            if (!SecurityHandler.isSignatureValid(signature, rawPayload)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid signature");
            }
            ObjectMapper mapper = new ObjectMapper();
            PullRequestPayload payload = mapper.readValue(rawPayload, PullRequestPayload.class);
            PullRequest pullRequest = createPullRequestFromBody(payload);
            pullRequestService.savePullRequest(pullRequest);
            return ResponseEntity.ok(pullRequest.getPullRequestId() + " " + pullRequest.getTitle() + " " + pullRequest.getDescription() + " " + pullRequest.getAuthor() + " " + pullRequest.getState() + " " + pullRequest.getHtmlUrl());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePullRequest(@RequestBody Map<String, Object> keyValueMap) {
        if (!keyValueMap.containsKey("api_key") || !SecurityHandler.isValidApiKey(keyValueMap.get("api_key").toString())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (keyValueMap.containsKey("id")) {
            pullRequestService.deletePullRequest((Long) keyValueMap.get("id"));
            return ResponseEntity.ok("ok");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id not found");
        }
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
