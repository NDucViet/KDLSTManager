package com.KDLST.Manager.Model.Entity.RateAFb;

import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import com.KDLST.Manager.Model.Entity.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedBack {
    private int feedbackID;
    private User user;
    private Services services;
    private String content;

}
