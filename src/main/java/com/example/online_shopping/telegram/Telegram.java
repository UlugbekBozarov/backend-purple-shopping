package com.example.online_shopping.telegram;

import com.example.online_shopping.telegram.domain.TelegramMessage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Telegram extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
    }

    public void setMessage(TelegramMessage message) {
        SendMessage sendMessage = new SendMessage()
                .setChatId((long) 789915105)
                .setText("<b>" + message.username + "</b>\n" +
                        "Email: " + message.email + "\n" +
                        "\n" + message.message)
                .setParseMode("HTML");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "ShoppingContactBot";
    }

    @Override
    public String getBotToken() {
        return "1788136272:AAGWPmVWBOJwGNl_7KCQ8srSNu9y4bKzRX0";
    }
}
