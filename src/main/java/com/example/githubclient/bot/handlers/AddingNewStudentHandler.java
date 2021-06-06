package com.example.githubclient.bot.handlers;

import com.example.githubclient.bot.BotState;
import com.example.githubclient.cache.UserDataCache;
import com.example.githubclient.model.telegram.UserData;
import com.example.githubclient.MessageTaker;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class AddingNewStudentHandler implements MessageHandler {

    private UserDataCache userDataCache;
    private final MessageTaker messageTaker;

    public AddingNewStudentHandler(UserDataCache userDataCache) {
        this.userDataCache = userDataCache;
        this.messageTaker = MessageTaker.getInstance();
    }

    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUserCurrentBotState(message.getFrom().getId()).equals(BotState.FILLING_NEW_STUDENT_DATA)) {
            userDataCache.setUserCurrentBotState(message.getFrom().getId(), BotState.ASK_FIRST_NAME);
        }
        return processUsersInput(message);
    }

    @Override
    public BotState getHandleName() {
        return BotState.FILLING_NEW_STUDENT_DATA;
    }

    private SendMessage processUsersInput(Message message) {
        String text = message.getText();
        int userId = message.getFrom().getId();
        long chatId = message.getChatId();

        UserData userData = userDataCache.getUserProfileData(userId);
        BotState botState = userDataCache.getUserCurrentBotState(userId);

        SendMessage reply = null;

        switch (botState) {
            case ASK_FIRST_NAME:
                reply = messageTaker.getReplyMessage(chatId, "reply.askStudentFirstName");
                userDataCache.setUserCurrentBotState(userId, BotState.ASK_LAST_NAME);
                break;

            case ASK_LAST_NAME:
                userData.setStudentFirstName(text);
                reply = messageTaker.getReplyMessage(chatId, "reply.askStudentLastName");
                userDataCache.setUserCurrentBotState(userId, BotState.ASK_GITHUB_LOGIN);
                break;
            case ASK_GITHUB_LOGIN:
                userData.setStudentLastName(text);
                reply = messageTaker.getReplyMessage(chatId, "reply.askStudentGitHubLogin");
                userDataCache.setUserCurrentBotState(userId, BotState.FILLED_STUDENT_ADDITION);
                break;

            case FILLED_STUDENT_ADDITION:
                userData.setStudentLogin(text);
                userDataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                reply = messageTaker.getReplyMessage(chatId, "reply.addedNewStudent");
                break;
        }

        userDataCache.saveUserProfileData(userId, userData);

        return reply;


    }
}
