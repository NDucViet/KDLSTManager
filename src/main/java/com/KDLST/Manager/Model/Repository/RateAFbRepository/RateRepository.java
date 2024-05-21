package com.KDLST.Manager.Model.Repository.RateAFbRepository;

import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Repository.ServiceProjectRepository.ServiceRepository;
import com.KDLST.Manager.Model.Repository.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.KDLST.Manager.Model.Entity.RateAFb.Rate;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class RateRepository {
    private ArrayList<Rate> rates = new ArrayList<>();
    @Autowired
    private ServiceRepository serviceRepository=new ServiceRepository();
    private UserRepository userRepository=new UserRepository();


    public ArrayList<Rate> getAll() {
        try {
            rates.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.Rate");
            while (rs.next()) {
                int rateID = rs.getInt("rateID");
                User userID=userRepository.getById(rs.getInt("userID"));
                int amountStar=rs.getInt("amountStar");
                Services serviceID=serviceRepository.getById(rs.getInt("serviceID"));

                Rate rate = new Rate(rateID,userID,amountStar,serviceID);
                rates.add(rate);
            }
            con.close();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        return rates;
    }
    public boolean update(Rate rate) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "update KDLST.Rate   set userID =?, serviceID=?,amountStar=?, where rateID =?");
            prsm.setInt(1, rate.getUser().getIdUser());
            prsm.setInt(2, rate.getServices().getServiceID());
            prsm.setInt(3, rate.getAmountStar());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }
    public boolean add(Rate rate) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into KDLST.Rate (userID, serviceID,amountStar) values(?,?,?)");
            prsm.setInt(1, rate.getUser().getIdUser());
            prsm.setInt(2, rate.getServices().getServiceID());
            prsm.setInt(3,rate.getAmountStar());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }


}
