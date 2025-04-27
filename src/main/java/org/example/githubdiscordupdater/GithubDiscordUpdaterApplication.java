package org.example.githubdiscordupdater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GithubDiscordUpdaterApplication {

    public static void main(String[] args) {
        DiscordManager.getInstance();
        SpringApplication.run(GithubDiscordUpdaterApplication.class, args);
    }

}
