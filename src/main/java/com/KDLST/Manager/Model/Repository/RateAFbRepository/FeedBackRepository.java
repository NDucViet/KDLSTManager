package com.KDLST.Manager.Model.Repository.RateAFbRepository;

import java.sql.*;
import java.util.ArrayList;

import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import com.KDLST.Manager.Model.Entity.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.RateAFb.FeedBack;
import com.KDLST.Manager.Model.Repository.ServiceProjectRepository.ServiceRepository;
import com.KDLST.Manager.Model.Repository.UserRepository.UserRepository;

@Repository
public class FeedBackRepository {
private ArrayList<FeedBack> feedBacks= new ArrayList<>();
    @Autowired
    private ServiceRepository serviceRepository=new ServiceRepository();
    private UserRepository userRepository=new UserRepository();
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
                User userID=userRepository.getById(rs.getInt("userID"));
                Services serviceID=serviceRepository.getById(rs.getInt("serviceID"));
                String content=rs.getString("content");
                FeedBack feedBack = new FeedBack(feedbackID,userID,serviceID,content);
                feedBacks.add(feedBack);
            }
            con.close();

        } catch (Exception e) {
            // TODO: handle exception
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
                    "update KDLST.Feedback set KDLST.Feedback.userID =?, KDLST.Feedback.serviceID=?, KDLST.Feedback.content = ?,where KDLST.Feedback.feedbackID =?");
            prsm.setInt(1, feedBack.getUser().getIdUser());
            prsm.setInt(2, feedBack.getServices().getServiceID());
            prsm.setString(3, feedBack.getContent());
            System.out.println(feedBack.toString());
            int result = prsm.executeUpdate();
            System.out.println(result);
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
            // TODO: handle exception
        }
        return false;
    }
    public boolean add(FeedBack feedBack) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into KDLST.Feedback (userID,serviceID,content) values(?,?,?)");
            prsm.setInt(1, feedBack.getUser().getIdUser());
            prsm.setInt(2, feedBack.getServices().getServiceID());
            prsm.setString(3, feedBack.getContent());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }



}
