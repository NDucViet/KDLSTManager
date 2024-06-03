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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Image image = (Image) obj;
        return blog.getBlogID() == (image.getBlog().getBlogID());
    }

    @Override
    public int hashCode() {
        return blog.getBlogID();
    }
}