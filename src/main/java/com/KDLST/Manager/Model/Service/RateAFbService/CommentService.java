package com.KDLST.Manager.Model.Service.RateAFbService;

import com.KDLST.Manager.Model.Entity.RateAFb.Comment;

import java.util.ArrayList;
public interface CommentService {

    public ArrayList<Comment> getAll();

    public Comment getById(int id);

    public boolean update(Comment comment);

    public boolean add(Comment comment);
    
    public ArrayList<Comment> getCommentByBlogID(int id);
}