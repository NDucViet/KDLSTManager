package com.KDLST.Manager.Model.Service.ImageBlogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KDLST.Manager.Model.Entity.ImageBlog.ImageBlog;
import com.KDLST.Manager.Model.Repository.ImageBlog.ImageBlogRepository;
import java.util.*;

@Service
public class ImageBlogServiceImplement implements ImageBlogService {
    private ArrayList<ImageBlog> ImageBlogList = new ArrayList<>();
    @Autowired
    ImageBlogRepository imageBlogRepository = new ImageBlogRepository();

    @Override
    public boolean add(ImageBlog imageBlog) {
        if (imageBlogRepository.add(imageBlog)) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<ImageBlog> getAll() {
        this.ImageBlogList = imageBlogRepository.getAll();
        if (this.ImageBlogList.isEmpty()) {
            return null;
        }
        return this.ImageBlogList;
    }

    @Override
    public ArrayList<ImageBlog> getImageByIdImageBlog(int id) {
        this.ImageBlogList = imageBlogRepository.getImageByIdImageBlog(id);
        if (this.ImageBlogList.isEmpty()) {
            return null;
        }
        return this.ImageBlogList;
    }

}
