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
                BlogType blogType = blogTypeRepository.getById(rs.getInt("blogTypeID"));
                String title = rs.getString("title");
                String contentOpen = rs.getString("contentOpen");
                Date dateTimeEdit = rs.getDate("dateTimeEdit");
                String contentBody = rs.getString("contentBody");
                Blog blog = new Blog(blogID, userID, blogType, title, contentOpen, dateTimeEdit, contentBody);
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
            BlogType blogType = blogTypeRepository.getById(rs.getInt("blogTypeID"));
            String title = rs.getString("title");
            String contentOpen = rs.getString("contentOpen");
            Date dateTimeEdit = rs.getDate("dateTimeEdit");
            String contentBody = rs.getString("contentBody");
            Blog blog = new Blog(blogID, userID, blogType, title, contentOpen, dateTimeEdit, contentBody);
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
                    "insert into KDLST.Blog (userID, blogTypeID, title, contentOpen, dateTimeEdit, contentBody) values(?,?,?,?,?,?)");
            prsm.setInt(1, blog.getUser().getIdUser());
            prsm.setInt(2, blog.getBlogType().getBlogTypeID());
            prsm.setString(3, blog.getTitle());
            prsm.setString(4, blog.getContentOpen());
            prsm.setDate(5, blog.getDateTimeEdit());
            prsm.setString(6, blog.getContentBody());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public ArrayList<Blog> getPageBlog(int index, int blogTypeID) {
        try {
            blogList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement stsm = con.prepareStatement("select * from KDLST.Blog"
            +"where blogTypeID = ?  order by blogID offset ? rows fetch next 9 rows only");
            stsm.setInt(1, blogTypeID);
            stsm.setInt(2, (index - 1) * 9);
            ResultSet rs = stsm.executeQuery();
            while (rs.next()) {
                int blogID = rs.getInt("blogID");
                User userID = userRepository.getById(rs.getInt("userID"));
                BlogType blogType = blogTypeRepository.getById(rs.getInt("blogTypeID"));
                String title = rs.getString("title");
                String contentOpen = rs.getString("contentOpen");
                Date dateTimeEdit = rs.getDate("dateTimeEdit");
                String contentBody = rs.getString("contentBody");
                Blog blog = new Blog(blogID, userID, blogType, title, contentOpen, dateTimeEdit, contentBody);
                blogList.add(blog);
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return blogList;
    }

    public static void main(String[] args) {
        // Date sqlDate = Date.valueOf("2023-05-29");
        // // Tạo đối tượng MyDateObject với thuộc tính ngày
        // BlogType blogType = new BlogType (1, "linh");
        // User user = new User(3, null, null, null, null, null, null, null, sqlDate, 0, null, null, null, null);
        // Blog blog = new Blog(1, user, blogType, "linh", "linh", sqlDate, "linh");
        BlogRepository blogRepository = new BlogRepository();
        // blogRepository.add(blog);
        System.out.println(blogRepository.getAll().size());
    }

}
