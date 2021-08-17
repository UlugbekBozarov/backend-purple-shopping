package com.example.online_shopping.database.domain;

import com.example.online_shopping.database.domain.enumeration.GENDER;
import com.example.online_shopping.database.domain.enumeration.STATUS;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import static com.example.online_shopping.database.domain.enumeration.STATUS.ACTIVE;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;

    @Setter
    private String fullname;

    @Setter
    @Column(unique = true)
    private String email;

    @Setter
    @NotNull
    @Column(nullable = false, unique = true)
    private String username;

    @Setter
    @NotNull
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Setter
    private STATUS status;

    @Setter
    private String role = "";

    @Setter
    private GENDER gender;

    @Setter
    private String birthday;

    @Setter
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserAddress> addresses;

    @Setter
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserContact> contacts;

    @Setter
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserCard> cards;

    private Date created_date;

    @Lob
    @Setter
    @Type(type = "org.hibernate.type.TextType")
    private String user_image;

    public Users(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = ACTIVE;
        this.created_date = new Date();
        this.user_image = "data:image/png;base64,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";
    }

    /*public Users(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.role = roles;
        this.status = ACTIVE;
        this.created_date = new Date();
        this.user_image = "image";

    }*/

}
