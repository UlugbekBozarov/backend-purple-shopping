package com.example.online_shopping.database.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "categories")
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NotBlank
    @Column(unique = true)
    private String type;

    @Setter
    private String icon;

    @OneToMany
    @JsonIgnore
    @JoinColumn(name = "category_id")
    private List<Products> products;

    public Categories(@NotBlank String type, String icon) {
        this.type = type;
        this.icon = icon;
    }
}
