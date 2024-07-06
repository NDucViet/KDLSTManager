package com.KDLST.Manager.Model.Repository.RateAFbRepository;

import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.Ticket.Ticket;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Repository.TicketRepository.TicketRepository;
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
    TicketRepository ticketRepository = new TicketRepository();
    private UserRepository userRepository = new UserRepository();

    public float getScoreByService(Ticket ticket) {
        try {
            rates.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement preparedStatement = con.prepareStatement("select * from KDLST.Rate where ticketID = ?");
            preparedStatement.setInt(1, ticket.getTicketID());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int rateID = rs.getInt("rateID");
                User userID = userRepository.getById(rs.getInt("userID"));
                int amountStar = rs.getInt("amountStar");
                Rate rate = new Rate(rateID, userID, amountStar, ticket);
                rates.add(rate);
            }
            con.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        if (rates.isEmpty() || rates == null) {
            return 0;
        }
        float score = 0;
        for (Rate rate : rates) {
            score += rate.getAmountStar();
        }
        score /= rates.size();
        return Float.parseFloat((String.format("%.1f", score)).replace(",", "."));
    }

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
                User userID = userRepository.getById(rs.getInt("userID"));
                int amountStar = rs.getInt("amountStar");
                Ticket ticket = ticketRepository.getById(rs.getInt("ticketID"));
                Rate rate = new Rate(rateID, userID, amountStar, ticket);
                rates.add(rate);
            }
            con.close();

        } catch (Exception e) {
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
                    "update KDLST.Rate   set userID =?, ticketID=?,amountStar=?, where rateID =?");
            prsm.setInt(1, rate.getUser().getIdUser());
            prsm.setInt(2, rate.getTicket().getTicketID());
            prsm.setInt(3, rate.getAmountStar());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean add(Rate rate) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into KDLST.Rate (userID, ticketID,amountStar) values(?,?,?)");
            prsm.setInt(1, rate.getUser().getIdUser());
            prsm.setInt(2, rate.getTicket().getTicketID());
            prsm.setInt(3, rate.getAmountStar());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

}
