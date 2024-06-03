package com.KDLST.Manager.Model.Service.ImageBlogService;

import java.util.ArrayList;

import com.KDLST.Manager.Model.Entity.ImageBlog.Image;

public interface ImageService {

    public ArrayList<Image> getAll();

    public Image getById(int id);

    public boolean update(Image image);

    public boolean add(Image image);

    public ArrayList<Image> getPageImage(int index, int blogTypeID);
    
    public ArrayList<Image> getImagesByBlogID(int blogID);
}
