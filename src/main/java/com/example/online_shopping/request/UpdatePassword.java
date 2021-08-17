package com.example.online_shopping.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdatePassword {

    @NotBlank
    private String password;
}
