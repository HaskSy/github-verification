package com.example.githubclient;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MessageTaker {

    private static MessageTaker instance;
    private Properties prop;
    private MessageTaker() {}

    public static MessageTaker getInstance() {
        if (instance == null) {
            instance = new MessageTaker();
            try (InputStream input = new FileInputStream("src/main/resources/messages.properties")) {

                instance.prop = new Properties();

                instance.prop.load(input);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return instance;
    }

    public SendMessage getReplyMessage(long chatId, String property) {
        return new SendMessage(chatId, getPropertyMessage(property));
    }

    public String getPropertyMessage(String property) {
        return this.prop.getProperty(property);
    }
}

