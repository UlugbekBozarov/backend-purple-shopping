package com.example.online_shopping.database.domain;

import com.example.online_shopping.database.domain.enumeration.COMMENT_STATUS;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.example.online_shopping.database.domain.enumeration.COMMENT_STATUS.SEND;


@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long comment_id;

    @NotBlank
    private String role;

    @Column(nullable = false)
    private Long type_id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private Users user;

    @NotBlank
    private String message;

    @Setter
    private Long answer;

    @NotNull
    private Date send_date;

    @Setter
    @NotNull
    private Date edit_date;

    @Setter
    private COMMENT_STATUS status;

    public Comments(Long type_id, Users user, String message, Long answer) {
        this.type_id = type_id;
        this.role = user.getRole();
        this.user = user;
        this.message = message;
        this.answer = answer;
        this.send_date = new Date();
        this.edit_date = new Date();
        this.status = SEND;
    }

    public void setMessage(String message) {
        this.message = message;
        this.edit_date = new Date();
    }
}
