package org.example.githubdiscordupdater;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import lombok.Getter;

import java.io.File;

@Getter
public class DiscordManager {
    private static DiscordManager instance;
    private GatewayDiscordClient client;

    private DiscordManager() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File("secrets.json"));
            String token = root.get("Token").asText();
            this.client = DiscordClientBuilder.create(token).build().login().block();
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static DiscordManager getInstance() {
        if (instance == null) {
            instance = new DiscordManager();
        }
        return instance;
    }
}
