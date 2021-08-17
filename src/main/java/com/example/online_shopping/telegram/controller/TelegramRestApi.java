package com.example.online_shopping.telegram.controller;

import com.example.online_shopping.erroe.ExceptionClass;
import com.example.online_shopping.erroe.NotAcceptable;
import com.example.online_shopping.erroe.message.ErrorMessage;
import com.example.online_shopping.response.message.Message;
import com.example.online_shopping.telegram.Telegram;
import com.example.online_shopping.telegram.domain.TelegramMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/telegram")
public class TelegramRestApi {

    @PostMapping("/send-message")
    public ResponseEntity<?> sendMessage(@Valid @RequestBody TelegramMessage message, BindingResult result) throws NotAcceptable {

        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            new Telegram().setMessage(message);
            log.info("Sent Message: {}", message.toString());
            return new ResponseEntity<>(new Message(201, "Your message has been sent!"), HttpStatus.CREATED);
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "Your message has not been sent!");
            throw new NotAcceptable("Update - Category", -1, error);
        }
    }
}
