package com.KDLST.Manager.Model.Repository.BlogRepository;

import java.util.ArrayList;
import java.sql.*;
import org.springframework.stereotype.Repository;

import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.Blog.BlogType;

import jakarta.el.ELException;

@Repository
public class BlogTypeRepository {
    private ArrayList<BlogType> blogTypeList = new ArrayList<>();

    public ArrayList<BlogType> getAll() {
        try {
            blogTypeList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.BlogType");
            while (rs.next()) {
                int blogTypeID = rs.getInt("blogTypeID");
                String blogTypeName = rs.getString("blogTypeName");
                BlogType blogType = new BlogType(blogTypeID, blogTypeName);
                blogTypeList.add(blogType);
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return blogTypeList;
    }

    public BlogType getById(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.BlogType where blogTypeID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find BlogType");
            }
            int blogTypeID = rs.getInt("blogTypeID");
            String blogTypeName = rs.getString("blogTypeName");
            BlogType blogType = new BlogType(blogTypeID, blogTypeName);
            st.close();
            return blogType;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean update(BlogType BlogType) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "update KDLST.BlogType set blogTypeName= ? where blogTypeID =?");
            prsm.setString(1, BlogType.getBlogTypeName());
            prsm.setInt(2, BlogType.getBlogTypeID());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean add(BlogType BlogType) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into KDLST.BlogType (blogTypeName) values(?)");
            prsm.setString(1, BlogType.getBlogTypeName());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
   
}
