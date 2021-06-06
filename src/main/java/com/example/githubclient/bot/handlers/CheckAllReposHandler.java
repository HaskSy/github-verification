package com.example.githubclient.bot.handlers;

import com.example.githubclient.bot.BotState;
import com.example.githubclient.cache.UserDataCache;
import com.example.githubclient.service.GitHubService;
import com.example.githubclient.MessageTaker;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

@Component
public class CheckAllReposHandler implements MessageHandler{

    private UserDataCache userDataCache;
    private GitHubService gitHubService;
    private MessageTaker messageTaker;

    public CheckAllReposHandler(UserDataCache userDataCache, GitHubService gitHubService) {
        this.userDataCache = userDataCache;
        this.gitHubService = gitHubService;
        this.messageTaker = MessageTaker.getInstance();
    }

    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUserCurrentBotState(message.getFrom().getId()).equals(BotState.SHOW_REPOS_LIST)) {
            userDataCache.setUserCurrentBotState(message.getFrom().getId(), BotState.SHOW_MAIN_MENU);
        }
        return processUsersInput(message);
    }

    @Override
    public BotState getHandleName() {
        return BotState.CHECK_ALL_REPOS;
    }

    private SendMessage processUsersInput(Message message) {
        int userId = message.getFrom().getId();
        long chatId = message.getChatId();

        try {
            gitHubService.checkAllRepos();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return messageTaker.getReplyMessage(chatId, "reply.allReposChecked");
    }
}
