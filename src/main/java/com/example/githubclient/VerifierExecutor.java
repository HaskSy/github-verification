package com.example.githubclient;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class VerifierExecutor {

    private final GitHubService service;

    public VerifierExecutor(GitHubService service) {
        this.service = service;
    }

    @Scheduled(cron = "*/5 * * ? * *")
    public void verify() {
        System.out.println("Влад Котов, где ТЗ? Ты же обещал");
    }

    @Scheduled(cron = "*/10 * * ? * *")
    public void sendTestMessage() throws IOException {

        String owner = "HaskSy";
        String repo = "java_au";
        int number = 44;

        service.sendVerificationMessage(owner, repo, number);
    }
}
