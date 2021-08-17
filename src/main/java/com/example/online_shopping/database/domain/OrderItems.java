package com.example.online_shopping.database.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long items_id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "product_id")
    private Products products;

    @NotNull
    private Integer count;

    @Column(nullable = false)
    @Positive
    private double total_price;


    public OrderItems(@NotNull Products products, @NotNull Integer count) {
        this.products = products;
        this.count = count;
        this.total_price = getTotalPrice(products.getPrice(), products.getStock(), count);
    }


    private double getTotalPrice(Double price, Integer stock, Integer quantity) {
        int s = 0;
        if (stock == null) {
            s = 0;
        } else {
            s = stock;
        }
        double total = price * quantity - price * quantity * s / 100;
        return total;
    }
}
