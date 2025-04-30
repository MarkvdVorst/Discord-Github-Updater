package org.example.githubdiscordupdater.repository;

import org.example.githubdiscordupdater.api.model.pullrequest.PullRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PullRequestRepository extends JpaRepository<PullRequestEntity, Long> {}
