package com.KDLST.Manager.Model.Service.BlogService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KDLST.Manager.Model.Entity.Blog.BlogType;
import com.KDLST.Manager.Model.Repository.BlogRepository.BlogTypeRepository;

@Service
public class BlogTypeServiceImplement implements BlogTypeService {

    private ArrayList<BlogType> blogTypeList = new ArrayList<>();
    @Autowired
    BlogTypeRepository blogTypeRepository = new BlogTypeRepository();

    @Override
    public boolean add(BlogType blogType) {
        if (blogTypeRepository.add(blogType)) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<BlogType> getAll() {
        this.blogTypeList = blogTypeRepository.getAll();
        if (!blogTypeList.isEmpty()) {
            return blogTypeList;
        }
        return null;
    }

    @Override
    public BlogType getById(int id) {
        if (blogTypeRepository.getById(id) != null) {
            return blogTypeRepository.getById(id);
        }
        return null;
    }

    @Override
    public boolean update(BlogType blogType) {
        if (blogTypeRepository.update(blogType)) {
            return true;
        }
        return false;
    }

}
