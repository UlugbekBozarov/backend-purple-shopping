package com.example.online_shopping.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrdersRequest {

    private Long address_id;

    private Long schedule;

    private Long contact_id;

    private String vaucher_code;

    private List<OrderItemsRequest> items;

}
