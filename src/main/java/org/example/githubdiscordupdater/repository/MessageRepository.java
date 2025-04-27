package org.example.githubdiscordupdater.repository;

import org.example.githubdiscordupdater.api.model.message.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {}
