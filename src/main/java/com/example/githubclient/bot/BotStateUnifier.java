package com.example.githubclient.bot;

import com.example.githubclient.bot.handlers.MessageHandler;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Component
public class BotStateUnifier {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TelegramProcessing.class);

    private HashMap<BotState, MessageHandler> messageHandlerMap = new HashMap<>();

    public BotStateUnifier(List<MessageHandler> messageHandlers) {
        messageHandlers.forEach(messageHandler ->
                this.messageHandlerMap.put(
                    messageHandler.getHandleName(),
                    messageHandler
                )
        );
    }

    public SendMessage execute(BotState currentState, Message message) throws IOException {
        MessageHandler currentMessageHandler = findMessageHandler(currentState);
        log.info("Starting handling MessageHandler: {}, Message: {}",
                currentMessageHandler.toString(),
                message.toString());
        return currentMessageHandler.handle(message);
    }

    private MessageHandler findMessageHandler(BotState currentState) {
        if (isFillingStudent(currentState)) {
            return messageHandlerMap.get(BotState.FILLING_NEW_STUDENT_DATA);
        }
        if (isFillingRepoCheck(currentState)) {
            return messageHandlerMap.get(BotState.CHECK_EXACT_REPO);
        }

        return messageHandlerMap.get(currentState);
    }

    private boolean isFillingStudent(BotState currentState) {
        switch (currentState) {
            case ASK_FIRST_NAME:
            case ASK_LAST_NAME:
            case ASK_GITHUB_LOGIN:
            case FILLING_NEW_STUDENT_DATA:
            case FILLED_STUDENT_ADDITION:
                return true;
            default:
                return false;
        }
    }

    private boolean isFillingRepoCheck(BotState currentState) {
        switch (currentState) {
            case ASK_CHECK_REPO_NAME:
            case ASK_OWNER_LOGIN:
            case CHECK_EXACT_REPO:
            case CHECKED_EXACT_REPO:
                return true;
            default:
                return false;
        }
    }
}
