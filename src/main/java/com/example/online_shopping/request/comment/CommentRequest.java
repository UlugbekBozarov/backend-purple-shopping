package com.example.online_shopping.request.comment;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    private Long answer;

    @NotBlank
    private String message;
}
