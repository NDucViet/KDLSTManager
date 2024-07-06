package com.KDLST.Manager.Model.Repository.RateAFbRepository;

import java.sql.*;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.RateAFb.FeedBack;
import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Repository.ServiceProjectRepository.ServiceRepository;
import com.KDLST.Manager.Model.Repository.UserRepository.UserRepository;

@Repository
public class FeedBackRepository {
    private ArrayList<FeedBack> feedBacks = new ArrayList<>();
    @Autowired
    private ServiceRepository serviceRepository = new ServiceRepository();
    private UserRepository userRepository = new UserRepository();

    public ArrayList<FeedBack> getAll() {
        try {
            feedBacks.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.Feedback");
            while (rs.next()) {
                int feedbackID = rs.getInt("feedbackID");
                User userID = userRepository.getById(rs.getInt("userID"));
                Services serviceID = serviceRepository.getById(rs.getInt("serviceID"));
                String content = rs.getString("content");
                Date date = rs.getDate("feedbackDate");
                FeedBack feedBack = new FeedBack(feedbackID, userID, serviceID, content, date);
                feedBacks.add(feedBack);
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return feedBacks;
    }

    public boolean update(FeedBack feedBack) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "update KDLST.Feedback set KDLST.Feedback.userID =?, KDLST.Feedback.serviceID=?, KDLST.Feedback.content = ?, KDLST.Feedback.feedbackDate=? where KDLST.Feedback.feedbackID =?");
            prsm.setInt(1, feedBack.getUser().getIdUser());
            prsm.setInt(2, feedBack.getServices().getServiceID());
            prsm.setString(3, feedBack.getContent());
            prsm.setDate(4, feedBack.getDate());
            prsm.setInt(5, feedBack.getFeedbackID());
            System.out.println(feedBack.toString());
            int result = prsm.executeUpdate();
            System.out.println(result);
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public ArrayList<FeedBack> getByIdService(Services service) {
        ArrayList<FeedBack> feedBacks = new ArrayList<>();
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.Feedback where KDLST.Feedback.serviceID=?;");
            st.setInt(1, service.getServiceID());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int feedbackID = rs.getInt("feedbackID");
                User userID = userRepository.getById(rs.getInt("userID"));
                String content = rs.getString("content");
                Date date = rs.getDate("feedbackDate");
                FeedBack feedBack = new FeedBack(feedbackID, userID, service, content, date);
                feedBacks.add(feedBack);
            }
            conn.close();
            return feedBacks;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean add(FeedBack feedBack) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into KDLST.Feedback (userID,serviceID,content,feedbackDate) values(?,?,?,?)");
            prsm.setInt(1, feedBack.getUser().getIdUser());
            prsm.setInt(2, feedBack.getServices().getServiceID());
            prsm.setString(3, feedBack.getContent());
            prsm.setDate(4, feedBack.getDate());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con
                    .prepareStatement("Delete from KDLST.Feedback where KDLST.Feedback.feedbackID =?");
            prsm.setInt(1, id);
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }
}
