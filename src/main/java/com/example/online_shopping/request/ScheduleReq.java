package com.example.online_shopping.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ScheduleReq {

    @NotBlank
    private String type;

    @NotBlank
    private String schedule_time;
}
