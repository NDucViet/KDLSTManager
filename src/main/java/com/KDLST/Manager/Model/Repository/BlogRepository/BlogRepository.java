package com.KDLST.Manager.Model.Repository.BlogRepository;

import java.util.ArrayList;
import java.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.Blog.Blog;
import com.KDLST.Manager.Model.Entity.Blog.BlogType;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Repository.UserRepository.UserRepository;

@Repository
public class BlogRepository {
    private ArrayList<Blog> blogList = new ArrayList<>();

    @Autowired
    BlogTypeRepository blogTypeRepository = new BlogTypeRepository();
    UserRepository userRepository = new UserRepository();

    public ArrayList<Blog> getAll() {
        try {
            blogList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.Blog");
            while (rs.next()) {
                int blogID = rs.getInt("blogID");
                User userID = userRepository.getById(rs.getInt("userID"));
                BlogType blogType = blogTypeRepository.getById(rs.getInt("blogTypeID"));
                String title = rs.getString("title");
                String contentOpen = rs.getString("contentOpen");
                Date dateTimeEdit = rs.getDate("dateTimeEdit");
                String contentBody = rs.getString("contentBody");
                boolean status = rs.getBoolean("status");
                Blog blog = new Blog(blogID, userID, blogType, title, contentOpen, dateTimeEdit, contentBody,status);
                blogList.add(blog);
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e + "hi");
        }
        return blogList;
    }

    public Blog getById(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.Blog where KDLST.Blog.blogID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                System.out.println("Cannot find Blog");
            }
            int blogID = rs.getInt("blogID");
            User userID = userRepository.getById(rs.getInt("userID"));
            BlogType blogType = blogTypeRepository.getById(rs.getInt("blogTypeID"));
            String title = rs.getString("title");
            String contentOpen = rs.getString("contentOpen");
            Date dateTimeEdit = rs.getDate("dateTimeEdit");
            String contentBody = rs.getString("contentBody");
            boolean status = rs.getBoolean("status");
            Blog blog = new Blog(blogID, userID, blogType, title, contentOpen, dateTimeEdit, contentBody, status);
            st.close();
            return blog;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean update(Blog blog) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "update KDLST.Blog set KDLST.Blog.userID= ?, blogTypeID = ?, title = ?, contentOpen = ?, dateTimeEdit = ?, contentBody= ? where KDLST.Blog.blogID =?");
            prsm.setInt(1, blog.getUser().getIdUser());
            prsm.setInt(2, blog.getBlogType().getBlogTypeID());
            prsm.setString(3, blog.getTitle());
            prsm.setString(4, blog.getContentOpen());
            prsm.setDate(5, blog.getDateTimeEdit());
            prsm.setString(6, blog.getContentBody());
            prsm.setInt(7, blog.getBlogID());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean add(Blog blog) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into KDLST.Blog (userID, blogTypeID, title, contentOpen, dateTimeEdit, contentBody,status) values(?,?,?,?,?,?,?)");
            prsm.setInt(1, blog.getUser().getIdUser());
            prsm.setInt(2, blog.getBlogType().getBlogTypeID());
            prsm.setString(3, blog.getTitle());
            prsm.setString(4, blog.getContentOpen());
            prsm.setDate(5, blog.getDateTimeEdit());
            prsm.setString(6, blog.getContentBody());
            prsm.setBoolean(7, true);
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public Blog getIdLastest() {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.Blog order by blogID desc limit 1;");
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                System.out.println("Cannot find Blog");
            }
            int blogID = rs.getInt("blogID");
            User userID = userRepository.getById(rs.getInt("userID"));
            BlogType blogType = blogTypeRepository.getById(rs.getInt("blogTypeID"));
            String title = rs.getString("title");
            String contentOpen = rs.getString("contentOpen");
            Date dateTimeEdit = rs.getDate("dateTimeEdit");
            String contentBody = rs.getString("contentBody");
            boolean status = rs.getBoolean("status");
            Blog blog = new Blog(blogID, userID, blogType, title, contentOpen, dateTimeEdit, contentBody, status);
            st.close();
            return blog;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

}
