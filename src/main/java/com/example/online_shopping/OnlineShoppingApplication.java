package com.example.online_shopping;

import com.example.online_shopping.telegram.Telegram;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@SpringBootApplication
public class OnlineShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShoppingApplication.class, args);

//        TelegramBotsApi botsApi =new TelegramBotsApi();
//        ApiContextInitializer.init();
//
//        try {
//            botsApi.registerBot(new Telegram());
//        } catch (TelegramApiRequestException e) {
//            e.printStackTrace();
//        }
    }

}
