
package com.KDLST.Manager.Model.Repository.BillRepository;

import java.util.ArrayList;
import java.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.LinkedHashMap;
import java.util.Map;
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
                int status = rs.getInt("status");
                BillDetails billDetails = new BillDetails(billDetailsID, billID, ticketID, quantity, total, status);
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
                    "select * from KDLST.BillDetails where KDLST.BillDetails.billDetailsID = ?;");
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
            int status = rs.getInt("status");
            BillDetails billDetails = new BillDetails(billDetailsID, billID, ticketID, quantity, total, status);
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
                    "update KDLST.BillDetails set KDLST.BillDetails.ticketID =?, KDLST.BillDetails.quantity=?, KDLST.BillDetails.total = ?,KDLST.BillDetails.status = ? where KDLST.BillDetails.billDetailsID =?");
            prsm.setInt(1, billDetails.getTicketID().getTicketID());// so sai
            prsm.setInt(2, billDetails.getQuantity());
            prsm.setDouble(3, billDetails.getTotal());
            prsm.setInt(4, billDetails.getStatus());
            prsm.setInt(5, billDetails.getBillDetailsID());
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
                    "insert into KDLST.BillDetails (billID, ticketID, quantity, total, status) values(?,?,?,?,?)");

            prsm.setInt(1, billDetails.getBillID().getBillID());
            prsm.setInt(2, billDetails.getTicketID().getTicketID());// so sai
            prsm.setInt(3, billDetails.getQuantity());
            prsm.setDouble(4, billDetails.getTotal());
            prsm.setInt(5, billDetails.getStatus());

            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public ArrayList<BillDetails> getByBillID(int id) {
        ArrayList<BillDetails> billDetailListt = new ArrayList<>();
        try {
            billDetailListt.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = con
                    .prepareStatement("select * from KDLST.BillDetails where KDLST.BillDetails.BillID = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int billDetailsID = rs.getInt("billDetailsID");
                Bill billID = billRepository.getById(rs.getInt("billID"));
                Ticket ticketID = ticketRepository.getById(rs.getInt("ticketID"));
                int quantity = rs.getInt("quantity");
                double total = rs.getDouble("total");
                int status = rs.getInt("status");
                BillDetails billDetails = new BillDetails(billDetailsID, billID, ticketID, quantity, total, status);
                billDetailListt.add(billDetails);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return billDetailListt;
    }

    public Map<String, Double> getMonthlyRevenue(String year) {
        Map<String, Double> revenueList = new LinkedHashMap<>();
        Connection con = null;
        PreparedStatement prsm = null;
        ResultSet rs = null;

        try {
            Class.forName(BaseConnection.nameClass);
            con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username, BaseConnection.password);
            prsm = con.prepareStatement(
                    "SELECT DATE_FORMAT(b.datePay, '%Y-%m') AS month, SUM(bd.total) AS total_amount " +
                            "FROM billdetails bd " +
                            "JOIN bill b ON bd.billID = b.billID " +
                            "GROUP BY DATE_FORMAT(b.datePay, '%Y-%m') " +
                            "ORDER BY month");

            rs = prsm.executeQuery();

            while (rs.next()) {
                String month = rs.getString("month");
                if (month.split("-")[0].equals(year)) {
                    double totalAmount = rs.getDouble("total_amount");
                    revenueList.put(String.valueOf(Integer.parseInt(month.split("-")[1])), totalAmount);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            // Đóng ResultSet, PreparedStatement và Connection
            try {
                if (rs != null)
                    rs.close();
                if (prsm != null)
                    prsm.close();
                if (con != null)
                    con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return revenueList;
    }

    public Map<String, Double> getYearsRevenue() {
        Map<String, Double> revenueList = new LinkedHashMap<>();
        Connection con = null;
        PreparedStatement prsm = null;
        ResultSet rs = null;

        try {
            Class.forName(BaseConnection.nameClass);
            con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username, BaseConnection.password);
            prsm = con.prepareStatement(
                    "SELECT DATE_FORMAT(b.datePay, '%Y-%m') AS month, SUM(bd.total) AS total_amount " +
                            "FROM billdetails bd " +
                            "JOIN bill b ON bd.billID = b.billID " +
                            "GROUP BY DATE_FORMAT(b.datePay, '%Y-%m') " +
                            "ORDER BY month");

            rs = prsm.executeQuery();

            while (rs.next()) {
                String month = rs.getString("month");

                double totalAmount = rs.getDouble("total_amount");
                revenueList.put(String.valueOf(Integer.parseInt(month.split("-")[0])), totalAmount);

            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            // Đóng ResultSet, PreparedStatement và Connection
            try {
                if (rs != null)
                    rs.close();
                if (prsm != null)
                    prsm.close();
                if (con != null)
                    con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return revenueList;
    }

    public Map<String, ArrayList<BillDetails>> getBillDetailsByUserID(int userID) {
        Map<String, ArrayList<BillDetails>> billDetailsMap = new LinkedHashMap<>();
        Connection con = null;
        PreparedStatement prsm = null;
        ResultSet rs = null;

        try {
            Class.forName(BaseConnection.nameClass);
            con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username, BaseConnection.password);
            prsm = con.prepareStatement(
                    "SELECT bd.*, b.datePay " +
                            "FROM billdetails bd " +
                            "JOIN bill b ON bd.billID = b.billID " +
                            "WHERE b.userID = ?");
            prsm.setInt(1, userID);
            rs = prsm.executeQuery();

            while (rs.next()) {
                int billDetailsID = rs.getInt("billDetailsID");
                Bill billID = billRepository.getById(rs.getInt("billID"));
                Ticket ticketID = ticketRepository.getById(rs.getInt("ticketID"));
                int quantity = rs.getInt("quantity");
                double total = rs.getDouble("total");
                String datePay = rs.getString("datePay");

                int status = rs.getInt("status");
                BillDetails billDetails = new BillDetails(billDetailsID, billID, ticketID, quantity, total, status);

                // Kiểm tra nếu key đã tồn tại, nếu không thì tạo mới ArrayList
                if (!billDetailsMap.containsKey(datePay)) {
                    billDetailsMap.put(datePay, new ArrayList<>());
                }
                // Thêm billDetails vào ArrayList tương ứng với key
                billDetailsMap.get(datePay).add(billDetails);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (prsm != null)
                    prsm.close();
                if (con != null)
                    con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return billDetailsMap;
    }

}