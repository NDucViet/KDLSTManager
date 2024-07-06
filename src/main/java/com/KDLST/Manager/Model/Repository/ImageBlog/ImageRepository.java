package com.KDLST.Manager.Model.Repository.ImageBlog;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.sql.*;
import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.Blog.Blog;
import com.KDLST.Manager.Model.Entity.ImageBlog.Image;
import com.KDLST.Manager.Model.Repository.BlogRepository.BlogRepository;

@Repository
public class ImageRepository {
    private ArrayList<Image> imageList = new ArrayList<>();

    @Autowired
    BlogRepository blogRepository = new BlogRepository();

    public ArrayList<Image> getAll() {
        try {
            imageList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.Image inner join Blog on Image.blogID = Blog.blogID where Blog.status = 1;");
            while (rs.next()) {
                int imageID = rs.getInt("imageID");
                String imageUrl = rs.getString("imageUrl");
                Blog blog = blogRepository.getById(rs.getInt("blogID"));
                String imageDescript = rs.getString("imageDescript");
                Image image = new Image(imageID, imageUrl, blog, imageDescript);
                imageList.add(image);
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return imageList;
    }

    public Image getById(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.Image where imageID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                System.out.println("cannot find  image");
            }
            int imageID = rs.getInt("imageID");
            String imageUrl = rs.getString("imageUrl");
            Blog blog = blogRepository.getById(rs.getInt("blogID"));
            String imageDescript = rs.getString("imageDescript");
            Image image = new Image(imageID, imageUrl, blog, imageDescript);
            conn.close();
            return image;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean update(Image image) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "update KDLST.Image set imageUrl= ?, imageDescript = ? where imageID =?");
            prsm.setString(1, image.getImageUrl());
            prsm.setString(2, image.getImageDescript());
            prsm.setInt(3, image.getImageID());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean add(Image image) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into KDLST.Image (imageUrl,blogID, imageDescript) values(?, ?,?)");
            prsm.setString(1, image.getImageUrl());
            prsm.setInt(2, image.getBlog().getBlogID());
            prsm.setString(3, image.getImageDescript());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public ArrayList<Image> getImagesByBlogID(int blogID) {
        try {
            imageList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement stsm = con.prepareStatement("select * from KDLST.Image where Image.blogID = ?");
            stsm.setInt(1, blogID);
            ResultSet rs = stsm.executeQuery();
            while (rs.next()) {
                int imageID = rs.getInt("imageID");
                String imageUrl = rs.getString("imageUrl");
                Blog blog = blogRepository.getById(rs.getInt("blogID"));
                String imageDescript = rs.getString("imageDescript");
                Image image = new Image(imageID, imageUrl, blog, imageDescript);
                imageList.add(image);
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return imageList;
    }

    public ArrayList<Image> getImagesByBlogTypeID(int blogTypeID) {
        try {
            imageList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement stsm = con.prepareStatement(
                    "select * from KDLST.Image inner join Blog on Image.blogID = Blog.blogID where blogTypeID = ?;");
            stsm.setInt(1, blogTypeID);
            ResultSet rs = stsm.executeQuery();
            while (rs.next()) {
                int imageID = rs.getInt("imageID");
                String imageUrl = rs.getString("imageUrl");
                Blog blog = blogRepository.getById(rs.getInt("blogID"));
                String imageDescript = rs.getString("imageDescript");
                Image image = new Image(imageID, imageUrl, blog, imageDescript);
                imageList.add(image);
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return imageList;
    }


    public ArrayList<Image> getImagesSortDate() {
        try {
            imageList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement stsm = con.prepareStatement(
                    "select * from Image inner join Blog on Image.blogID = Blog.blogID where Blog.status = 1 order by dateTimeEdit desc limit 6;");
            ResultSet rs = stsm.executeQuery();
            while (rs.next()) {
                int imageID = rs.getInt("imageID");
                String imageUrl = rs.getString("imageUrl");
                Blog blog = blogRepository.getById(rs.getInt("blogID"));
                String imageDescript = rs.getString("imageDescript");
                Image image = new Image(imageID, imageUrl, blog, imageDescript);
                imageList.add(image);
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return imageList;
    }

}
