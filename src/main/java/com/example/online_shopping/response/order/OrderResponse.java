package com.example.online_shopping.response.order;

import lombok.Data;

import java.util.Date;

@Data
public class OrderResponse {

    private Long order_id;

    private Date order_date;

    private Double total;

    private Integer items_length;

    private String delivery_time;

    private String location;

    private Boolean vaucher;

    public OrderResponse(Long order_id, Date order_date, Double total, Integer items_length, String delivery_time, String location, boolean vaucher) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.total = total;
        this.items_length = items_length;
        this.delivery_time = delivery_time;
        this.location = location;
        this.vaucher = vaucher;
    }

}
