package com.example.online_shopping.database.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "user_address")
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long address_id;

    @NotNull
    @JsonIgnore
    private Long user_id;

    @Setter
    @NotBlank
    private String title;

    @Setter
    @NotBlank
    private String address;

    public UserAddress(Long user_id, String title, String address) {
        this.user_id = user_id;
        this.title = title;
        this.address = address;
    }
}
