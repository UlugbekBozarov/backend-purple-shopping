package com.example.online_shopping.erroe.message;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private int code;

    private String message;
}
