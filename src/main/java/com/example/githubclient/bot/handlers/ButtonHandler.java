package com.example.githubclient.bot.handlers;

import com.example.githubclient.bot.BotState;
import com.example.githubclient.service.ButtonService;
import com.example.githubclient.MessageTaker;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class ButtonHandler implements MessageHandler {
    private final MessageTaker messageTaker;
    private final ButtonService buttonService;

    public ButtonHandler(ButtonService buttonService) {
        this.messageTaker = MessageTaker.getInstance();
        this.buttonService = buttonService;
    }

    @Override
    public SendMessage handle(Message message) {
        return buttonService.getMainMenuMessage(message.getChatId(), messageTaker.getPropertyMessage("reply.showMainMenu"));
    }

    @Override
    public BotState getHandleName() {
        return BotState.SHOW_MAIN_MENU;
    }

}
