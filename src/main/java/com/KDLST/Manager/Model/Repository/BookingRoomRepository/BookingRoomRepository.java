package com.KDLST.Manager.Model.Repository.BookingRoomRepository;

import java.sql.*;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoom;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Repository.UserRepository.UserRepository;
import jakarta.el.ELException;

@Repository
public class BookingRoomRepository {
    ArrayList<BookingRoom> bookingRoomList = new ArrayList<>();
    @Autowired
    UserRepository userRepository = new UserRepository();

    public ArrayList<BookingRoom> getAll() {
        try {
            bookingRoomList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.BookingRoom");
            while (rs.next()) {
                int bookingRoomID = rs.getInt("bookingRoomID");
                User user = userRepository.getById(rs.getInt("userID"));
                Date startDate = rs.getDate("checkInDate");
                Date endDate = rs.getDate("checkOutDate");
                boolean status = rs.getBoolean("status");
                BookingRoom bookingRoom = new BookingRoom(bookingRoomID, user, startDate, endDate, status);
                bookingRoomList.add(bookingRoom);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e);
        }
        return bookingRoomList;
    }

    public ArrayList<BookingRoom> getByIdUser(int id) {
        ArrayList<BookingRoom> bkrs = new ArrayList<>();
        try {
            bkrs.clear();
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.BookingRoom where KDLST.BookingRoom.userID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int bookingRoomID = rs.getInt("bookingRoomID");
                User user = userRepository.getById(rs.getInt("userID"));
                Date startDate = rs.getDate("checkInDate");
                Date endDate = rs.getDate("checkOutDate");
                boolean status = rs.getBoolean("status");
                BookingRoom bookingRoom = new BookingRoom(bookingRoomID, user, startDate, endDate, status);
                bkrs.add(bookingRoom);
            }

            st.close();
            return bkrs;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public BookingRoom getById(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.BookingRoom where KDLST.BookingRoom.bookingRoomID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find");
            }
            int bookingRoomID = rs.getInt("bookingRoomID");
            User user = userRepository.getById(rs.getInt("userID"));
            Date startDate = rs.getDate("checkInDate");
            Date endDate = rs.getDate("checkOutDate");
            boolean status = rs.getBoolean("status");
            BookingRoom bookingRoom = new BookingRoom(bookingRoomID, user, startDate, endDate, status);
            st.close();
            return bookingRoom;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public ArrayList<BookingRoom> getByStatus() {
        ArrayList<BookingRoom> bookingRoomLis = new ArrayList<>();
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.BookingRoom where KDLST.BookingRoom.status = ?;");
            st.setBoolean(1, true);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int bookingRoomID = rs.getInt("bookingRoomID");
                User user = userRepository.getById(rs.getInt("userID"));
                Date startDate = rs.getDate("checkInDate");
                Date endDate = rs.getDate("checkOutDate");
                boolean status = rs.getBoolean("status");
                BookingRoom bookingRoom = new BookingRoom(bookingRoomID, user, startDate, endDate, status);
                bookingRoomLis.add(bookingRoom);
            }
            st.close();
            return bookingRoomLis;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean update(BookingRoom bookingRoom) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "update KDLST.User set KDLST.BookingRoom.userID =?, KDLST.BookingRoom.checkInDate=?, KDLST.BookingRoom.checkOutDate = ?, KDLST.BookingRoom.status =? where KDLST.BookingRoom.bookingRoomID =?");
            prsm.setInt(1, bookingRoom.getUser().getIdUser());
            prsm.setDate(2, bookingRoom.getStartDate());
            prsm.setDate(3, bookingRoom.getEndDate());
            prsm.setBoolean(4, bookingRoom.isStatus());
            prsm.setInt(5, bookingRoom.getBookingRoomID());
            int result = prsm.executeUpdate();
            System.out.println(result);
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e);
        }
        return false;
    }

    public boolean add(BookingRoom bookingRoom) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into KDLST.BookingRoom (userID, checkInDate, checkOutDate, status) values(?,?,?,?)");
            prsm.setInt(1, bookingRoom.getUser().getIdUser());
            prsm.setDate(2, bookingRoom.getStartDate());
            prsm.setDate(3, bookingRoom.getEndDate());
            prsm.setBoolean(4, bookingRoom.isStatus());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
