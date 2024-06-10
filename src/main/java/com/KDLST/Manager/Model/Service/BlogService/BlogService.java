package com.KDLST.Manager.Model.Service.BlogService;

import com.KDLST.Manager.Model.Entity.Blog.Blog;
import java.util.ArrayList;

public interface BlogService {

    public ArrayList<Blog> getAll();

    public Blog getById(int id);

    public boolean update(Blog blog);

    public boolean add(Blog blog);

    public Blog getIdLastest();
}