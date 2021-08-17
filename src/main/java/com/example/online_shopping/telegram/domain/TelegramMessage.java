package com.example.online_shopping.telegram.domain;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TelegramMessage {

    public String username;

    @NotBlank
    public String email;

    @NotBlank
    public String message;
}
