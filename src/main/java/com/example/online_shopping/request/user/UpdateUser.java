package com.example.online_shopping.request.user;

import com.example.online_shopping.database.domain.enumeration.GENDER;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUser {

    private String fullname;

    private String birthday;

    private GENDER gender;

    private String image;
}
