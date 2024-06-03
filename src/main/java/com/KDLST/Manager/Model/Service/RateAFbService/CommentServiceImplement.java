package com.KDLST.Manager.Model.Service.RateAFbService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KDLST.Manager.Model.Entity.RateAFb.Comment;
import com.KDLST.Manager.Model.Repository.RateAFbRepository.CommentRepository;
import java.util.*;

@Service
public class CommentServiceImplement implements CommentService {
    ArrayList<Comment> commentList = new ArrayList<>();
    @Autowired
    CommentRepository commentRepository = new CommentRepository();

    @Override
    public boolean add(Comment Comment) {
        if (commentRepository.add(Comment)) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Comment> getAll() {
        this.commentList = commentRepository.getAll();
        if (!commentList.isEmpty()) {
            return commentList;
        }
        return null;
    }

    @Override
    public Comment getById(int id) {
        if (commentRepository.getById(id) != null) {
            return commentRepository.getById(id);
        }
        return null;
    }

    @Override
    public boolean update(Comment Comment) {
        if (commentRepository.update(Comment)) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Comment> getCommentByBlogID(int id){
        commentList = commentRepository.getCommentByBlogID(id);
        if(!commentList.isEmpty()){
            return commentList;
        }
        return null;
    }
}
