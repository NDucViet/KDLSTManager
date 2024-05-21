package com.KDLST.Manager.Model.Service.BlogService;

import java.util.ArrayList;

import com.KDLST.Manager.Model.Entity.Blog.BlogType;

public interface BlogTypeService {

    public ArrayList<BlogType> getAll();

    public BlogType getById(int id);

    public boolean update(BlogType blogType);

    public boolean add(BlogType blogType);
}