package org.example.githubdiscordupdater.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageEditSpec;
import discord4j.rest.util.Color;
import org.example.githubdiscordupdater.DiscordManager;
import org.example.githubdiscordupdater.api.model.pullrequest.PullRequest;
import org.example.githubdiscordupdater.api.model.pullrequest.PullRequestEntity;
import org.example.githubdiscordupdater.api.model.message.MessageEntity;
import org.example.githubdiscordupdater.api.model.util.ReviewStates;
import org.example.githubdiscordupdater.repository.MessageRepository;
import org.example.githubdiscordupdater.repository.PullRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.example.githubdiscordupdater.api.model.util.Conversions.createEntityFromPullRequest;
import static org.example.githubdiscordupdater.api.model.util.Conversions.createPullRequestFromEntity;

@Service
public class PullRequestService {

    @Autowired
    private PullRequestRepository pullRequestRepository;
    @Autowired
    private MessageRepository messageRepository;

    public List<PullRequest> getPullRequests() {
        List<PullRequestEntity> pullRequestEntities = pullRequestRepository.findAll();
        List<PullRequest> pullRequests = new ArrayList<>();
        for (PullRequestEntity pullRequestEntity : pullRequestEntities) {
            pullRequests.add(createPullRequestFromEntity(pullRequestEntity));
        }
        return pullRequests;
    }

    public void savePullRequest(PullRequest pullRequest) {
        DiscordManager manager = DiscordManager.getInstance();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File("secrets.json"));
            String channelId = root.get("ChannelId").asText();
            MessageEntity messageEntity = messageRepository.findById(pullRequest.getPullRequestId()).orElse(null);
            TextChannel channel = manager.getClient().getChannelById(Snowflake.of(channelId)).ofType(TextChannel.class).block();
            if (channel != null) {
                String stateMessage = getStateMessage(pullRequest);
                EmbedCreateSpec.Builder embedBuilder = EmbedCreateSpec.builder()
                        .thumbnail("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fpngimg.com%2Fuploads%2Fgithub%2Fgithub_PNG80.png")
                        .title("Pull Request Update")
                        .description(pullRequest.getTitle())
                        .color(Color.of(0x3498db))  // Set embed color (blue in this case)
                        .author(pullRequest.getAuthor(), pullRequest.getAuthorUrl(), pullRequest.getAvatarUrl());
                if (pullRequest.getDescription() != null)
                    embedBuilder.addField("Description", pullRequest.getDescription(), false);
                EmbedCreateSpec embed = embedBuilder.addField("Link", pullRequest.getUrl(), true)
                        .addField("", stateMessage, false)
                        .footer("Last updated", "")
                        .timestamp(Instant.now())
                        .build();
                if (messageEntity != null && messageEntity.getMessageId() != null) {
                    Message message = channel.getMessageById(Snowflake.of(messageEntity.getMessageId())).block();
                    MessageEditSpec messageEditSpec = MessageEditSpec.builder().addEmbed(embed).build();
                    assert message != null;
                    message.edit(messageEditSpec).block();
                } else {
                    Message message = channel.createMessage(embed).block();
                    MessageEntity newMessage = new MessageEntity();
                    newMessage.setPullRequestId(pullRequest.getPullRequestId());
                    assert message != null;
                    newMessage.setMessageId(message.getId().asLong());
                    messageRepository.save(newMessage);
                }
            }
            PullRequestEntity pullRequestEntity = createEntityFromPullRequest(pullRequest);
            pullRequestRepository.save(pullRequestEntity);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getStateMessage(PullRequest pullRequest) {
        String stateMessage;
        if (pullRequest.getReview() != null && pullRequest.getReview().getState() != null && pullRequest.getReview().getState() == ReviewStates.CHANGES_REQUESTED) {
            stateMessage = "Status: \uD83D\uDFE7 CHANGES REQUESTED";
        } else {
            stateMessage = switch (pullRequest.getState()) {
                case "open" -> "Status: \uD83D\uDFE9 OPEN";
                case "closed" -> "Status: \uD83D\uDFE5 CLOSED";
                default -> "Status: \uD83D\uDFE8 UNKNOWN";
            };
        }
        return stateMessage;
    }
}
