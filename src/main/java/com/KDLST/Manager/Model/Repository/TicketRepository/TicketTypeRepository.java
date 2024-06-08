
package com.KDLST.Manager.Model.Repository.TicketRepository;

import java.util.ArrayList;
import org.springframework.stereotype.Repository;
import java.sql.*;
import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.Ticket.TicketType;
import jakarta.el.ELException;

@Repository
public class TicketTypeRepository {
    private static ArrayList<TicketType> ticketTypeList = new ArrayList<>();

    public ArrayList<TicketType> getAll() {
        try {
            ticketTypeList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.TicketType");
            while (rs.next()) {
                int ticketTypeID = rs.getInt("ticketTypeID");
                String ticketTypeName = rs.getString("ticketTypeName");
                TicketType ticketType = new TicketType(ticketTypeID, ticketTypeName);
                ticketTypeList.add(ticketType);
            }
            con.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return ticketTypeList;
    }

    public TicketType getById(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.TicketType where KDLST.TicketType.ticketTypeID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find");
            }
            int ticketTypeID = rs.getInt("ticketTypeID");
            String ticketTypeName = rs.getString("ticketTypeName");
            TicketType ticketType = new TicketType(ticketTypeID, ticketTypeName);
            st.close();
            return ticketType;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args) {
        TicketTypeRepository t = new TicketTypeRepository();
        System.out.println(t.getById(5).toString());
    }

    public boolean update(TicketType ticketType) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "update KDLST.TicketType   set ticketTypeName =? where ticketTypeID =?");
            prsm.setString(1, ticketType.getTicketTypeName());
            prsm.setInt(2, ticketType.getTicketTypeID());

            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    public boolean add(TicketType ticketType) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into KDLST.TicketType (ticketTypeName) values(?)");
            prsm.setString(1, ticketType.getTicketTypeName());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }
}