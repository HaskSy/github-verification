package com.example.githubclient.cache;

import com.example.githubclient.bot.BotState;
import com.example.githubclient.bot.TelegramProcessing;
import com.example.githubclient.model.telegram.UserData;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public interface DataCache {

    Logger log = org.slf4j.LoggerFactory.getLogger(TelegramProcessing.class);

    void setUserCurrentBotState(int userId, BotState botState);

    BotState getUserCurrentBotState(int userId);

    UserData getUserProfileData(int userId);

    void saveUserProfileData(int userId, UserData userData);
}
