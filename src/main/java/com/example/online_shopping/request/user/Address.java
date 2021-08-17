package com.example.online_shopping.request.user;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class Address {

    @NotBlank
    private String title;

    @NotBlank
    private String address;
}
