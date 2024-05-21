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

import jakarta.el.ELException;

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
                BlogType blogTypeID = blogTypeRepository.getById(rs.getInt("blogTypeID"));
                String title = rs.getString("title");
                String content = rs.getString("content");
                Date dateTimeEdit = rs.getDate("dateTimeEdit");
                Blog blog = new Blog(blogID, userID, blogTypeID, title, content, dateTimeEdit);
                blogList.add(blog);
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
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
                throw new ELException("Cannot find");
            }
            int blogID = rs.getInt("blogID");
            User userID = userRepository.getById(rs.getInt("userID"));
            BlogType blogTypeID = blogTypeRepository.getById(rs.getInt("blogTypeID"));
            String title = rs.getString("title");
            String content = rs.getString("content");
            Date dateTimeEdit = rs.getDate("dateTimeEdit");
            Blog blog = new Blog(blogID, userID, blogTypeID, title, content, dateTimeEdit);
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
                    "update KDLST.Blog set KDLST.Blog.userID= ?, blogTypeID = ?, title = ?, content = ?, dateTimeEdit = ? where KDLST.Blog.blogID =?");
            prsm.setInt(1, blog.getUser().getIdUser());
            prsm.setInt(2, blog.getBlogType().getBlogTypeID());
            prsm.setString(3, blog.getTitle());
            prsm.setString(4, blog.getContent());
            prsm.setDate(5, blog.getDateTimeEdit());
            prsm.setInt(6, blog.getBlogID());
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
                    "insert into KDLST.Blog (userID, blogTypeID, title, content, dateTimeEdit) values(?,?,?,?,?)");
            prsm.setInt(1, blog.getUser().getIdUser());
            prsm.setInt(2, blog.getBlogType().getBlogTypeID());
            prsm.setString(3, blog.getTitle());
            prsm.setString(4, blog.getContent());
            prsm.setDate(5, blog.getDateTimeEdit());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
