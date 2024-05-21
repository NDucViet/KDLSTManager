package com.KDLST.Manager.Model.Entity.RateAFb;

import com.KDLST.Manager.Model.Entity.Blog.Blog;
import com.KDLST.Manager.Model.Entity.User.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int commentID;
    private User user;
    private Blog blog;
    private String content;
    private Date commentDate;
}
