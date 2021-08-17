package com.example.online_shopping.erroe;

import com.example.online_shopping.erroe.message.ErrorMessage;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SystemError extends RuntimeException {

    private String method;

    private long id;

    private ErrorMessage error;
}
