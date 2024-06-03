package com.KDLST.Manager.Model.Entity.ImageBlog;

import com.KDLST.Manager.Model.Entity.Blog.Blog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private int imageID;
    private String imageUrl;
    private Blog blog;
    private String imageDescript;
}