package com.KDLST.Manager.Model.Service.BlogService;

import com.KDLST.Manager.Model.Entity.Blog.Blog;
import com.KDLST.Manager.Model.Repository.BlogRepository.BlogRepository;


import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImplement implements BlogService {

    private ArrayList<Blog> blogList = new ArrayList<>();

    @Autowired
    BlogRepository blogRepository = new BlogRepository();

    @Override
    public boolean add(Blog blog) {
        if (blogRepository.add(blog)) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Blog> getAll() {
        this.blogList = blogRepository.getAll();
        if (!blogList.isEmpty()) {
            return blogList;
        }
        return null;
    }

    @Override
    public Blog getById(int id) {
        if (blogRepository.getById(id) != null) {
            return blogRepository.getById(id);
        }
        return null;
    }

    @Override
    public boolean update(Blog blog) {
        if (blogRepository.update(blog)) {
            return true;
        }
        return false;
    }
}
