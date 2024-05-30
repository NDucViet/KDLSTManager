package com.KDLST.Manager.Model.Entity.Blog;

import java.sql.Date;
import com.KDLST.Manager.Model.Entity.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    private int blogID;
    private User user;
    private BlogType blogType;
    private String title;
    private String content;
    private Date dateTimeEdit;
}