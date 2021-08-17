package com.example.online_shopping.database.domain;

import com.example.online_shopping.database.domain.enumeration.VAUCHER;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vaucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vaucher_id;

    @Setter
    private VAUCHER status;

    private String vaucher_code;
}
