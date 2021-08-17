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
@Table(name = "user_contact")
public class UserContact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long contact_id;

    @NotNull
    @JsonIgnore
    private Long user_id;

    @Setter
    @NotBlank
    private String contact;

    public UserContact(Long user_id, String contact) {
        this.user_id = user_id;
        this.contact = contact;
    }
}
