package com.example.githubclient;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VerifierExecutor {

    @Scheduled(cron = "*/5 * * ? * *")
    public static void verify() {
        System.out.println("Влад Котов, где ТЗ? Ты же обещал");
    }

}
