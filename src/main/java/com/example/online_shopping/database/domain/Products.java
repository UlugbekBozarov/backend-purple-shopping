package com.example.online_shopping.database.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@ToString
@NoArgsConstructor
@Table(name = "products")
public class Products {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;

    @Getter
    @Setter
    @NotBlank
    private String product_name;

    @Getter
    @Setter
    @NotNull
    private Long category_id;

    @Getter
    @Setter
    @PositiveOrZero
    private Integer stock;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @Positive
    private Double price;

    @Lob
    @Getter
    @Setter
    @Type(type = "org.hibernate.type.TextType")
    private String image_source;

    public Products(@NotBlank String product_name, @NotNull Long category_id, Integer stock, String description, @NotNull Double price, String image_source) {
        this.product_name = product_name;
        this.category_id = category_id;
        this.stock = stock;
        this.description = description;
        this.price = price;
        this.image_source = image_source;
    }
}
