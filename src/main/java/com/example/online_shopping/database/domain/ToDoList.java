package com.example.online_shopping.database.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "to_do_list")
public class ToDoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private Users user;

    @Setter
    private boolean status;

    @Setter
    @NotBlank
    private String task;

    public ToDoList(Users user, String task) {
        this.user = user;
        status = false;
        this.task = task;
    }
}
