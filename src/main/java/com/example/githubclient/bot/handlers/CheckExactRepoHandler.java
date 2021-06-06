package com.example.githubclient.bot.handlers;

import com.example.githubclient.bot.BotState;
import com.example.githubclient.cache.UserDataCache;
import com.example.githubclient.model.telegram.UserData;
import com.example.githubclient.service.GitHubService;
import com.example.githubclient.MessageTaker;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

public class CheckExactRepoHandler implements MessageHandler {

    private UserDataCache userDataCache;
    private GitHubService gitHubService;
    private final MessageTaker messageTaker;

    public CheckExactRepoHandler(UserDataCache userDataCache, GitHubService gitHubService) {
        this.userDataCache = userDataCache;
        this.gitHubService = gitHubService;
        this.messageTaker = MessageTaker.getInstance();
    }

    @Override
    public SendMessage handle(Message message) throws IOException {
        if (userDataCache.getUserCurrentBotState(message.getFrom().getId()).equals(BotState.CHECK_EXACT_REPO)) {
            userDataCache.setUserCurrentBotState(message.getFrom().getId(), BotState.ASK_OWNER_LOGIN);
        }
        return processUsersInput(message);
    }

    @Override
    public BotState getHandleName() {
        return BotState.CHECK_EXACT_REPO;
    }

    private SendMessage processUsersInput(Message message) throws IOException {
        String text = message.getText();
        int userId = message.getFrom().getId();
        long chatId = message.getChatId();

        UserData userData = userDataCache.getUserProfileData(userId);
        BotState botState = userDataCache.getUserCurrentBotState(userId);

        SendMessage reply = null;

        switch (botState) {
            case ASK_OWNER_LOGIN:
                reply = messageTaker.getReplyMessage(chatId, "reply.askOwnerLogin");
                userDataCache.setUserCurrentBotState(userId, BotState.ASK_CHECK_REPO_NAME);
                break;

            case ASK_CHECK_REPO_NAME:
                userData.setStudentLogin(text);
                reply = messageTaker.getReplyMessage(chatId, "reply.askRepoName");
                userDataCache.setUserCurrentBotState(userId, BotState.CHECKED_EXACT_REPO);
                break;
            case CHECKED_EXACT_REPO:
                userData.setRepoName(text);
                gitHubService.checkRepo(userData.getStudentLogin(), userData.getRepoName());
                reply = messageTaker.getReplyMessage(chatId, "reply.exactRepoChecked");
                userDataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                break;

        }

        userDataCache.saveUserProfileData(userId, userData);

        return reply;
    }
}
