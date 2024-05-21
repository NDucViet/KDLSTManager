package com.KDLST.Manager.Model.Entity.ImageBlog;

import com.KDLST.Manager.Model.Entity.Blog.Blog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageBlog {
    private Blog blog;
    private Image image;
}
