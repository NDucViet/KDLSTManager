
package com.KDLST.Manager.Model.Repository.BillRepository;

import java.util.ArrayList;
import java.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.Bill.Bill;
import com.KDLST.Manager.Model.Entity.Bill.BillDetails;
import com.KDLST.Manager.Model.Entity.Ticket.Ticket;
import com.KDLST.Manager.Model.Repository.TicketRepository.TicketRepository;

import jakarta.el.ELException;

@Repository
public class BillDetailsRepository {
    private static ArrayList<BillDetails> billDetailList = new ArrayList<>();
    @Autowired
    private BillRepository billRepository = new BillRepository();
    TicketRepository ticketRepository = new TicketRepository();

    public ArrayList<BillDetails> getAll() {
        try {
            billDetailList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.BillDetails");
            while (rs.next()) {
                int billDetailsID = rs.getInt("billDetailsID");
                Bill billID = billRepository.getById(rs.getInt("billID"));
                Ticket ticketID = ticketRepository.getById(rs.getInt("ticketID"));
                int quantity = rs.getInt("quantity");
                double total = rs.getDouble("total");
                BillDetails billDetails = new BillDetails(billDetailsID, billID, ticketID, quantity, total);
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
                    "select * from KDLST.BillDetails where KDLST.BillDetails.billID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find");
            }
            int billDetailsID = rs.getInt("billDetailsID");
            Bill billID = billRepository.getById(rs.getInt("billID"));
            Ticket ticketID = ticketRepository.getById(rs.getInt("ticketID"));
            int quantity = rs.getInt("quantity");
            double total = rs.getDouble("total");
            BillDetails billDetails = new BillDetails(billDetailsID, billID, ticketID, quantity, total);
            conn.close();
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
                    "update KDLST.BillDetails set KDLST.BillDetails.ticketID =?, KDLST.BillDetails.quantity=?, KDLST.BillDetails.total = ? where KDLST.BillDetails.billDetailsID =?");
            prsm.setInt(1, billDetails.getTicketID().getTicketID());// so sai
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
                    "insert into KDLST.BillDetails (billID, ticketID, quantity, total) values(?,?,?,?)");

            prsm.setInt(1, billDetails.getBillID().getBillID());
            prsm.setInt(2, billDetails.getTicketID().getTicketID());// so sai
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