package com.KDLST.Manager.Model.Repository.ImageBlog;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import java.sql.*;

import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.ImageBlog.Image;

import jakarta.el.ELException;

@Repository
public class ImageRepository {
    private ArrayList<Image> imageList = new ArrayList<>();

    public ArrayList<Image> getAll() {
        try {
            imageList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.Image");
            while (rs.next()) {
                int imageID = rs.getInt("imageID");
                String imageUrl = rs.getString("imageUrl");
                Image image = new Image(imageID, imageUrl);
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
                throw new ELException("Cannot find");
            }
            int imageID = rs.getInt("imageID");
            String imageUrl = rs.getString("imageUrl");
            Image image = new Image(imageID, imageUrl);
            st.close();
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
                    "update KDLST.Image set imageUrl= ? where imageID =?");
            prsm.setString(1, image.getImageUrl());
            prsm.setInt(2, image.getImageID());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean add(Image Image) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into KDLST.Image (imageUrl) values(?)");
            prsm.setString(1, Image.getImageUrl());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
