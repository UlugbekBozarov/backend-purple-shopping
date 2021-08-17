package com.example.online_shopping.request.product;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank
    private String product_name;

    @NotNull
    private Long category_id;

    private Integer stock;

    @NotNull
    private Double price;

    private String description;

    @NotNull
    private String image_source;
}
