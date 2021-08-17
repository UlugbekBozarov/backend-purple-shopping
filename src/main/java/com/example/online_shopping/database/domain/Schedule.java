package com.example.online_shopping.database.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schedule_id;

    @Setter
    @Column(unique = true)
    private String type;

    @Setter
    private String schedule_time;

    public Schedule(String type, String schedule_time) {
        this.type = type;
        this.schedule_time = schedule_time;
    }
}
