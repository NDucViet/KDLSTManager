package com.KDLST.Manager.Model.Repository.ImageBlog;

import java.sql.*;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.Blog.Blog;
import com.KDLST.Manager.Model.Entity.ImageBlog.Image;
import com.KDLST.Manager.Model.Entity.ImageBlog.ImageBlog;
import com.KDLST.Manager.Model.Repository.BlogRepository.BlogRepository;

import jakarta.el.ELException;

@Repository
public class ImageBlogRepository {
    private ArrayList<ImageBlog> ImageBlogList = new ArrayList<>();

    @Autowired
    BlogRepository blogRepository = new BlogRepository();
    ImageRepository ImageRepository = new ImageRepository();

    public ArrayList<ImageBlog> getAll() {
        try {
            ImageBlogList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.ImageBlog");
            while (rs.next()) {
                Blog blog = blogRepository.getById(rs.getInt("blogID"));
                Image image = ImageRepository.getById(rs.getInt("imageID"));
                ImageBlog ImageBlog = new ImageBlog(blog, image);
                ImageBlogList.add(ImageBlog);
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return ImageBlogList;
    }

    public ArrayList<ImageBlog> getImageByIdImageBlog(int id) {
        ImageBlogList.clear();
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.ImageBlog.imageID where blogID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find");
            }
            while (rs.next()) {
                Blog blog = blogRepository.getById(rs.getInt("blogID"));
                Image image = ImageRepository.getById(rs.getInt("imageID"));
                ImageBlog ImageBlog = new ImageBlog(blog, image);
                ImageBlogList.add(ImageBlog);
            }

            st.close();
            return ImageBlogList;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    // public boolean update(ImageBlog ImageBlog) {
    // try {
    // Class.forName(BaseConnection.nameClass);
    // Connection con = DriverManager.getConnection(BaseConnection.url,
    // BaseConnection.username,
    // BaseConnection.password);
    // PreparedStatement prsm = con.prepareStatement(
    // "update KDLST.ImageBlog set KDLST.ImageBlog.blogID= ? where
    // KDLST.ImageBlog.ImageBlogID =?");
    // // prsm.setString(1, ImageBlog.get());
    // // prsm.setInt(2, ImageBlog.getImageBlogID());
    // int result = prsm.executeUpdate();
    // con.close();
    // return result > 0;
    // } catch (Exception e) {
    // System.out.println(e);
    // }
    // return false;
    // }

    public boolean add(ImageBlog imageBlog) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into KDLST.ImageBlog (blogID, imageID) values(?,?)");
            prsm.setInt(1, imageBlog.getBlog().getBlogID());
            prsm.setInt(2, imageBlog.getImage().getImageID());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
