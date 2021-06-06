package com.example.githubclient.bot.handlers;

import com.example.githubclient.bot.BotState;
import com.example.githubclient.cache.UserDataCache;
import com.example.githubclient.service.ButtonService;
import com.example.githubclient.MessageTaker;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class StartHandler implements MessageHandler {

    private UserDataCache userDataCache;
    private ButtonService buttonService;
    private MessageTaker messageTaker;

    public StartHandler(UserDataCache userDataCache, ButtonService buttonService) {
        this.userDataCache = userDataCache;
        this.messageTaker = MessageTaker.getInstance();
        this.buttonService = buttonService;
    }

    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUserCurrentBotState(message.getFrom().getId()).equals(BotState.START)) {
            userDataCache.setUserCurrentBotState(message.getFrom().getId(), BotState.SHOW_MAIN_MENU);
        }
        return processUsersInput(message);
    }

    @Override
    public BotState getHandleName() {
        return BotState.START;
    }

    private SendMessage processUsersInput(Message message) {
        int userId = message.getFrom().getId();
        long chatId = message.getChatId();

        SendMessage reply = buttonService.getMainMenuMessage(chatId, messageTaker.getPropertyMessage("reply.welcomeMessage"));
        userDataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
        return reply;


    }

}
