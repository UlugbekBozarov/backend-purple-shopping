package com.example.online_shopping.database.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "user_card")
public class UserCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long card_id;

    @NotNull
    @JsonIgnore
    private Long user_id;

    @NotNull
    private Integer card_number_1;

    @NotNull
    private Integer card_number_2;

    @NotNull
    private Integer card_number_3;

    @NotNull
    private Integer card_number_4;

    @NotBlank
    private String card_date;

    public UserCard (Long user_id, Integer card_number_1, Integer card_number_2, Integer card_number_3, Integer card_number_4, String card_date) {
        this.user_id = user_id;
        this.card_number_1 = card_number_1;
        this.card_number_2 = card_number_2;
        this.card_number_3 = card_number_3;
        this.card_number_4 = card_number_4;
        this.card_date = card_date;
    }
}
