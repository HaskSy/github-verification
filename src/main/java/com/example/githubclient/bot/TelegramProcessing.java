package com.example.githubclient.bot;

import com.example.githubclient.cache.UserDataCache;
import com.example.githubclient.service.ButtonService;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;


@Component
public class TelegramProcessing {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TelegramProcessing.class);


    private BotStateUnifier botStateUnifier;
    private UserDataCache userDataCache;
    private ButtonService buttonService;

    public TelegramProcessing(BotStateUnifier botStateUnifier, UserDataCache userDataCache, ButtonService buttonService) {
        this.botStateUnifier = botStateUnifier;
        this.userDataCache = userDataCache;
        this.buttonService = buttonService;
    }

    public BotApiMethod<?> handleUpdate(Update update) throws IOException {
        SendMessage replyMessage = null;

        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                log.info("New message user: {}, user_id: {}, chat_id: {}, text: {}",
                        message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
                replyMessage = handleInputMessage(message);
            }
        }
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) throws IOException {
        String input = message.getText();
        int userId = message.getFrom().getId();
        BotState botState;
        SendMessage replyMessage;

        switch (input) {
            case "/start":
                botState = BotState.START;
                break;
            case "Список студентов":
                botState = BotState.SHOW_STUDENTS_LIST;
                break;
            case "Список всех репозиториев":
                botState = BotState.SHOW_REPOS_LIST;
                break;
            case "Проверить конкретный репозиторий":
                botState = BotState.CHECK_EXACT_REPO;
                break;
            case "Проверить все репозитории":
                botState = BotState.CHECK_ALL_REPOS;
                break;
            case "Добавить студента":
                botState = BotState.FILLING_NEW_STUDENT_DATA;
                break;
            case "Удалить студента":
                botState = BotState.START;
                break;
            default:
                botState = userDataCache.getUserCurrentBotState(userId);
                break;
        }
        log.info("BotState was set to: {}, case: {}",
                botState, input);

        userDataCache.setUserCurrentBotState(userId, botState);

        replyMessage = botStateUnifier.execute(botState, message);

        return replyMessage;
    }


}
