package com.example.online_shopping.request.user;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignUp {

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
