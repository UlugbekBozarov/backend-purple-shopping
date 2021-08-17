package com.example.online_shopping.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsRequest {

    private Long product_id;

    private int quantity;
}
