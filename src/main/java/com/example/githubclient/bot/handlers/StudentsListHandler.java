package com.example.githubclient.bot.handlers;

import com.example.githubclient.bot.BotState;
import com.example.githubclient.cache.UserDataCache;
import com.example.githubclient.model.db.dbStudent;
import com.example.githubclient.service.DatabaseService;
import com.example.githubclient.MessageTaker;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Iterator;
import java.util.List;

@Component
public class StudentsListHandler implements MessageHandler {

    private UserDataCache userDataCache;
    private DatabaseService databaseService;
    private MessageTaker messageTaker;


    public StudentsListHandler(UserDataCache userDataCache, DatabaseService databaseService) {
        this.userDataCache = userDataCache;
        this.databaseService = databaseService;
        this.messageTaker = MessageTaker.getInstance();
    }

    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUserCurrentBotState(message.getFrom().getId()).equals(BotState.SHOW_STUDENTS_LIST)) {
            userDataCache.setUserCurrentBotState(message.getFrom().getId(), BotState.SHOW_MAIN_MENU);
        }
        return processUsersInput(message);
    }

    @Override
    public BotState getHandleName() {
        return BotState.SHOW_STUDENTS_LIST;
    }

    private SendMessage processUsersInput(Message message) {
        int userId = message.getFrom().getId();
        long chatId = message.getChatId();

        List<dbStudent> studentList = databaseService.getStudents();
        StringBuilder textMessage = new StringBuilder(messageTaker.getPropertyMessage("reply.showStudentsList") + "\n");
        Iterator<dbStudent> iterator = studentList.iterator();
        while (iterator.hasNext()) {
            textMessage.append(iterator.next().toString());
            if (iterator.hasNext()) {
                textMessage.append("\n");
            }
            else {
                break;
            }
        }
        SendMessage reply = new SendMessage(chatId, textMessage.toString());
        userDataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
        return reply;

    }

}
