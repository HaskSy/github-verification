package com.example.githubclient.bot;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;


public class TelegramBot extends TelegramWebhookBot {

    private String webHookPath;
    private String botUsername;
    private String botToken;

    private TelegramProcessing telegramProcessing;

    public TelegramBot(DefaultBotOptions botOptions, TelegramProcessing telegramProcessing) {
        super(botOptions);
        this.telegramProcessing = telegramProcessing;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }

    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return telegramProcessing.handleUpdate(update);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
