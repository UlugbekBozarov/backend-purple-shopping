package com.example.online_shopping.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateAdmin {

    @NotBlank
    String username;

    @NotBlank
    String email;

    @NotBlank
    String password;
}
