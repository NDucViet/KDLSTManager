package com.KDLST.Manager.Model.Repository.BillRepository;

import java.util.ArrayList;
import java.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.Bill.Bill;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Repository.UserRepository.UserRepository;

import jakarta.el.ELException;

@Repository
public class BillRepository {
    ArrayList<Bill> billList = new ArrayList<>();
    @Autowired
    private UserRepository userRepository = new UserRepository();

    public ArrayList<Bill> getAll() {
        try {
            billList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.bill");
            while (rs.next()) {
                int billID = rs.getInt("billID");
                User user = userRepository.getById(rs.getInt("userID"));
                Date datePay = rs.getDate("datePay");
                Date dateUse = rs.getDate("dateUse");
                int status = rs.getInt("status");
                Bill bill = new Bill(billID, user, datePay, dateUse, status);
                billList.add(bill);
            }
            con.close();
        } catch (Exception e) {

        }
        return billList;
    }

    public Bill getById(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.bill where KDLST.bill.billID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find");
            }
            int billID = rs.getInt("billID");
            User user = userRepository.getById(rs.getInt("userID"));
            Date datePay = rs.getDate("datePay");
            Date dateUse = rs.getDate("dateUse");
            int status = rs.getInt("status");
            Bill bill = new Bill(billID, user, datePay, dateUse, status);
            conn.close();
            return bill;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public ArrayList<Bill> getByIdUser(int id) {

        try {
            billList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.bill where KDLST.bill.userID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int billID = rs.getInt("billID");
                User user = userRepository.getById(rs.getInt("userID"));
                Date datePay = rs.getDate("datePay");
                Date dateUse = rs.getDate("dateUse");
                int status = rs.getInt("status");
                Bill bill = new Bill(billID, user, datePay, dateUse, status);
                billList.add(bill);
            }
            conn.close();
            return billList;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean update(Bill bill) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "Update KDLST.bill set userID = ?, datePay = ?, status = ?, dateUse=? WHERE billID = ?");
            prsm.setInt(1, bill.getUser().getIdUser());
            prsm.setDate(2, bill.getDatePay());
            prsm.setInt(3, bill.getStatus());
            prsm.setDate(4, bill.getDateUse());
            prsm.setInt(5, bill.getBillID());
            System.out.println(bill.toString());
            int result = prsm.executeUpdate();
            System.out.println(result);
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean add(Bill bill) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "INSERT INTO KDLST.bill (userID, datePay, status, dateUse) VALUES (?, ?, ?, ?)");
            prsm.setInt(1, bill.getUser().getIdUser());
            prsm.setDate(2, bill.getDatePay());
            prsm.setInt(3, bill.getStatus());
            prsm.setDate(4, bill.getDateUse());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
