package com.KDLST.Manager.Model.Repository.TicketRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.Ticket.Ticket;
import com.KDLST.Manager.Model.Entity.Ticket.TicketSold;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Repository.UserRepository.UserRepository;

import jakarta.el.ELException;

@Repository
public class TicketSoldRepository {
    private static ArrayList<TicketSold> ticketSoldList = new ArrayList<>();

    @Autowired
    TicketRepository ticketRepository = new TicketRepository();
    UserRepository userRepository = new UserRepository();

    public ArrayList<TicketSold> getAll() {
        try {
            ticketSoldList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.TicketSold ");
            while (rs.next()) {
                String id = rs.getString("id");
                Ticket ticket = ticketRepository.getById(rs.getInt("ticketID"));
                User user = userRepository.getById(rs.getInt("userID"));
                Date usageDate = rs.getDate("usageDate");
                String barcode = rs.getString("barcode");
                int status = rs.getInt("status");
                TicketSold ticketSold = new TicketSold(id, ticket, user, usageDate, barcode, status);
                ticketSoldList.add(ticketSold);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return ticketSoldList;
    }

    public ArrayList<TicketSold> getByUserID(int UserID) {
        try {
            ticketSoldList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = con.prepareStatement(
                    "select * from KDLST.TicketSold where userID = ?;");
            st.setInt(1, UserID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                Ticket ticket = ticketRepository.getById(rs.getInt("ticketID"));
                User user = userRepository.getById(rs.getInt("userID"));
                Date usageDate = rs.getDate("usageDate");
                String barcode = rs.getString("barcode");
                int status = rs.getInt("status");
                TicketSold ticketSold = new TicketSold(id, ticket, user, usageDate, barcode, status);
                ticketSoldList.add(ticketSold);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return ticketSoldList;
    }

    public TicketSold getById(String id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.TicketSold where KDLST.TicketSold.id = ?;");
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find");
            }
            id = rs.getString("id");
            Ticket ticket = ticketRepository.getById(rs.getInt("ticketID"));
            User user = userRepository.getById(rs.getInt("userID"));
            Date usageDate = rs.getDate("usageDate");
            String barcode = rs.getString("barcode");
            int status = rs.getInt("status");
            TicketSold ticketSold = new TicketSold(id, ticket, user, usageDate, barcode, status);
            conn.close();
            return ticketSold;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean update(TicketSold ticketSold) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "update KDLST.TicketSold  set status =? where ticketSoldID =?");
            prsm.setInt(1, 0);
            prsm.setString(2, ticketSold.getId());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean add(TicketSold ticketSold) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into KDLST.TicketSold (id, ticketID, userID, usageDate, barcode, status) values(?,?,?,?,?,?)");
            prsm.setString(1, ticketSold.getId());
            prsm.setInt(2, ticketSold.getTicket().getTicketID());
            prsm.setInt(3, ticketSold.getUser().getIdUser());
            prsm.setDate(4, ticketSold.getUsageDate());
            prsm.setString(5, ticketSold.getBarcode());
            prsm.setInt(6, ticketSold.getStatus());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static void main(String[] args) {
        TicketSoldRepository ticketSoldRepository = new TicketSoldRepository();
        System.out.println(ticketSoldRepository.getByUserID(1));
    }
}
