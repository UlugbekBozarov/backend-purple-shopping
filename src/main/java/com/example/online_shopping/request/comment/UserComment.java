package com.example.online_shopping.request.comment;

import com.example.online_shopping.database.domain.enumeration.COMMENT_STATUS;
import lombok.*;

import javax.persistence.Lob;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserComment {

    private Long user_id;

    private String fullname;

    private String username;

    private String role;

    @Lob
    private String user_image;

    private String last_message;

    private int send_message;

    private Date send_date;

    private Date user_date;

    private COMMENT_STATUS status;

}
