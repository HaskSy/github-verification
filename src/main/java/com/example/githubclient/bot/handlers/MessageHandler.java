package com.example.githubclient.bot.handlers;

import com.example.githubclient.bot.BotState;
import com.example.githubclient.bot.TelegramProcessing;
import org.slf4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

public interface MessageHandler {

    Logger log = org.slf4j.LoggerFactory.getLogger(TelegramProcessing.class);

    SendMessage handle(Message message) throws IOException;

    BotState getHandleName();
}
