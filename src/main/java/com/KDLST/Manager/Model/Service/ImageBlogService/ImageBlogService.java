package com.KDLST.Manager.Model.Service.ImageBlogService;

import com.KDLST.Manager.Model.Entity.ImageBlog.ImageBlog;

import java.util.ArrayList;

public interface ImageBlogService {

    public ArrayList<ImageBlog> getAll();

    public ArrayList<ImageBlog> getImageByIdImageBlog(int id);

    public boolean add(ImageBlog imageBlog);
}