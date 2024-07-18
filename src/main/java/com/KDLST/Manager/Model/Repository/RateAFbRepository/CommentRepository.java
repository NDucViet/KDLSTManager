package com.KDLST.Manager.Model.Repository.RateAFbRepository;

import java.util.ArrayList;
import java.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.Blog.Blog;
import com.KDLST.Manager.Model.Entity.RateAFb.Comment;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Repository.BlogRepository.BlogRepository;
import com.KDLST.Manager.Model.Repository.UserRepository.UserRepository;

import jakarta.el.ELException;

@Repository
public class CommentRepository {
    private ArrayList<Comment> commentList = new ArrayList<>();

    @Autowired
    UserRepository userRepository = new UserRepository();
    BlogRepository blogRepository = new BlogRepository();

    public ArrayList<Comment> getAll() {
        try {
            commentList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.Comment");
            while (rs.next()) {
                int commentID = rs.getInt("commentID");
                User user = userRepository.getById(rs.getInt("userID"));
                Blog blog = blogRepository.getById(rs.getInt("blogID"));
                String content = rs.getString("content");
                Date commentDate = rs.getDate("commentDate");
                Comment comment = new Comment(commentID, user, blog, content, commentDate);
                commentList.add(comment);
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return commentList;
    }

    public Comment getById(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.Comment where commentID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find");
            }
            int commentID = rs.getInt("commentID");
            User user = userRepository.getById(rs.getInt("userID"));
            Blog blog = blogRepository.getById(rs.getInt("blogID"));
            String content = rs.getString("content");
            Date commentDate = rs.getDate("commentDate");
            Comment comment = new Comment(commentID, user, blog, content, commentDate);
            conn.close();
            return comment;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean update(Comment comment) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "update KDLST.Comment set userID= ?, blogID = ?, content = ?, commentDate = ? where commentID =?");
            prsm.setInt(1, comment.getUser().getIdUser());
            prsm.setInt(2, comment.getBlog().getBlogID());
            prsm.setString(3, comment.getContent());
            prsm.setDate(4, comment.getCommentDate());
            prsm.setInt(5, comment.getCommentID());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean add(Comment comment) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into KDLST.Comment (userID, blogID, content, commentDate) values(?,?,?,?)");
            prsm.setInt(1, comment.getUser().getIdUser());
            prsm.setInt(2, comment.getBlog().getBlogID());
            prsm.setString(3, comment.getContent());
            prsm.setDate(4, comment.getCommentDate());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public ArrayList<Comment> getCommentByBlogID(int id) {
        try {
            commentList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.Comment where blogID = ? order by commentID desc;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int commentID = rs.getInt("commentID");
                User user = userRepository.getById(rs.getInt("userID"));
                Blog blog = blogRepository.getById(rs.getInt("blogID"));
                String content = rs.getString("content");
                Date commentDate = rs.getDate("commentDate");
                Comment comment = new Comment(commentID, user, blog, content, commentDate);
                commentList.add(comment);
            }
            conn.close();
            return commentList;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean delete(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con
                    .prepareStatement("Delete from KDLST.Comment where KDLST.Comment.commentID =?");
            prsm.setInt(1, id);
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
        }
        return false;
    }

    public static void main(String[] args) {
        CommentRepository commentRepository = new CommentRepository();
        System.out.println(commentRepository.delete(12));
    }
}
