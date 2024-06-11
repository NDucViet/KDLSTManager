package com.KDLST.Manager.Model.Service.ImageBlogService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KDLST.Manager.Model.Entity.ImageBlog.Image;
import com.KDLST.Manager.Model.Repository.ImageBlog.ImageRepository;

@Service
public class ImageServiceImplement implements ImageService {

    private ArrayList<Image> imageList = new ArrayList<>();

    @Autowired
    ImageRepository imageRepository = new ImageRepository();

    @Override
    public ArrayList<Image> getAll() {
        this.imageList = imageRepository.getAll();
        if (!imageList.isEmpty()) {
            return imageList;
        }
        return null;
    }

    @Override
    public Image getById(int id) {
        if (imageRepository.getById(id) != null) {
            return imageRepository.getById(id);
        }
        return null;
    }

    @Override
    public boolean update(Image Image) {
        if (imageRepository.update(Image)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(Image Image) {
        if (imageRepository.add(Image)) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Image> getImagesByBlogID(int blogID) {
        ArrayList<Image> imageList = imageRepository.getImagesByBlogID(blogID);
        if (!imageList.isEmpty()) {
            return imageList;
        }
        return null;
    }

    @Override
    public ArrayList<Image> getImagesByBlogTypeID(int blogTypeID) {
        ArrayList<Image> imageList = imageRepository.getImagesByBlogTypeID(blogTypeID);
        if (!imageList.isEmpty()) {
            return imageList;
        }
        return null;
    }

    @Override
    public ArrayList<Image> getImagesSortDate() {
        ArrayList<Image> imageList = imageRepository.getAll();
        if (!imageList.isEmpty()) {
            return imageList;
        }
        return null;
    }
}
