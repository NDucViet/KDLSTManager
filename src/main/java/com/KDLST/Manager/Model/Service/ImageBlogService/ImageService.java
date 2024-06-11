package com.KDLST.Manager.Model.Service.ImageBlogService;

import java.util.ArrayList;

import com.KDLST.Manager.Model.Entity.ImageBlog.Image;

public interface ImageService {

    public ArrayList<Image> getAll();

    public Image getById(int id);

    public boolean update(Image image);

    public boolean add(Image image);

    public ArrayList<Image> getImagesByBlogID(int blogID);

    public ArrayList<Image> getImagesByBlogTypeID(int blogTypeID);

    public ArrayList<Image> getImagesSortDate();
}
