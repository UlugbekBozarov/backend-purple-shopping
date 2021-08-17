package com.example.online_shopping.request.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Contact {

    @NotBlank
    private String contact;
}
