package org.example.githubdiscordupdater.api.model.message;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MessageEntity {
    @Id
    private Long pullRequestId;
    private Long messageId;
}
