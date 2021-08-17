package com.example.online_shopping.request.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CardRequest {

    @NotBlank
    private String card_number;

    @NotNull
    private String card_date;
}
