
package com.KDLST.Manager.Model.Repository.BillRepository;

import java.util.ArrayList;
import java.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.Bill.Bill;
import com.KDLST.Manager.Model.Entity.Bill.BillDetails;

import jakarta.el.ELException;

@Repository
public class BillDetailsRepository {
    private static ArrayList<BillDetails> billDetailList = new ArrayList<>();
    @Autowired
    private BillRepository billRepository;

    public ArrayList<BillDetails> getAll() {
        try {
            billDetailList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.BillDetail");
            while (rs.next()) {
                Bill billID = billRepository.getById(rs.getInt("billID"));
                int ticketID = rs.getInt("ticketID");
                int quantity = rs.getInt("quantity");
                double total = rs.getDouble("total");
                BillDetails billDetails = new BillDetails(billID, ticketID, quantity, total);
                billDetailList.add(billDetails);
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e);
        }
        return billDetailList;
    }

    public BillDetails getById(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.BillDetail where KDLST.BillDetail.billID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find");
            }
            Bill billID = billRepository.getById(rs.getInt("billID"));
            int ticketID = rs.getInt("ticketID");
            int quantity = rs.getInt("quantity");
            double total = rs.getDouble("total");
            BillDetails billDetails = new BillDetails(billID, ticketID, quantity, total);
            st.close();
            return billDetails;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean update(BillDetails billDetails) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "update KDLST.BillDetail set KDLST.BillDetail.ticketID =?, KDLST.BillDetail.quantity=?, KDLST.BillDetail.total = ? where KDLST.BillDetail.billID =?");
            prsm.setInt(1, billDetails.getTicketID());// so sai
            prsm.setInt(2, billDetails.getQuantity());
            prsm.setDouble(3, billDetails.getTotal());
            prsm.setInt(4, billDetails.getBillID().getBillID());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean add(BillDetails billDetails) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into KDLST.BillDetail (billID, ticketID, quantity, total) values(?,?,?,?)");

            prsm.setInt(1, billDetails.getBillID().getBillID());
            prsm.setInt(2, billDetails.getTicketID());// so sai
            prsm.setInt(3, billDetails.getQuantity());
            prsm.setDouble(4, billDetails.getTotal());

            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

}