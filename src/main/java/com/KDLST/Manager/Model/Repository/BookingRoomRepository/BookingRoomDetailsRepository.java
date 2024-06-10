package com.KDLST.Manager.Model.Repository.BookingRoomRepository;

import java.sql.*;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoom;
import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoomDetails;
import com.KDLST.Manager.Model.Entity.Hotel.Room;
import com.KDLST.Manager.Model.Repository.HotelRepository.RoomRepository;

import jakarta.el.ELException;

@Repository
public class BookingRoomDetailsRepository {
    ArrayList<BookingRoomDetails> bookingRoomDetailsList = new ArrayList<>();

    @Autowired
    BookingRoomRepository bookingRoomRepository = new BookingRoomRepository();
    RoomRepository roomRepository = new RoomRepository();

    public ArrayList<BookingRoomDetails> getAll() {
        try {
            bookingRoomDetailsList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.BookingRoomDetail");
            while (rs.next()) {
                int bookingRoomDetailsID = rs.getInt("bookingRoomDetailID");
                BookingRoom bookingRoom = bookingRoomRepository.getById(rs.getInt("bookingRoomID"));
                Room room = roomRepository.getById(rs.getInt("roomID"));
                BookingRoomDetails bookingRoomDetails = new BookingRoomDetails(bookingRoomDetailsID, bookingRoom, room);
                bookingRoomDetailsList.add(bookingRoomDetails);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e);
        }
        return bookingRoomDetailsList;
    }

    public ArrayList<BookingRoomDetails> getByIDRoomDetails(int id) {
        ArrayList<BookingRoomDetails> bkrdt = new ArrayList<>();
        try {
            bkrdt.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = con.prepareStatement(
                    "select * from KDLST.BookingRoomDetail where KDLST.BookingRoomDetail.bookingRoomID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int bookingRoomDetailsID = rs.getInt("bookingRoomDetailID");
                BookingRoom bookingRoom = bookingRoomRepository.getById(rs.getInt("bookingRoomID"));
                Room room = roomRepository.getById(rs.getInt("roomID"));
                BookingRoomDetails bookingRoomDetails = new BookingRoomDetails(bookingRoomDetailsID, bookingRoom, room);
                bkrdt.add(bookingRoomDetails);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e);
        }
        return bkrdt;
    }

    public BookingRoomDetails getById(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.BookingRoomDetail where KDLST.BookingRoomDetail.bookingRoomDetailID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find");
            }
            int bookingRoomDetailsID = rs.getInt("bookingRoomDetailID");
            BookingRoom bookingRoom = bookingRoomRepository.getById(rs.getInt("bookingRoomID"));
            Room room = roomRepository.getById(rs.getInt("roomID"));
            BookingRoomDetails bookingRoomDetails = new BookingRoomDetails(bookingRoomDetailsID, bookingRoom, room);
            st.close();
            return bookingRoomDetails;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    public boolean update(BookingRoomDetails bookingRoomDetails) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "update KDLST.BookingRoomDetail set KDLST.BookingRoomDetail.bookingRoomID =?, KDLST.BookingRoomDetail.roomID = ? where KDLST.BookingRoomDetail.bookingRoomDetailID =?");
            prsm.setInt(1, bookingRoomDetails.getBookingRoom().getBookingRoomID());
            prsm.setInt(2, bookingRoomDetails.getRoom().getRoomID());
            prsm.setInt(3, bookingRoomDetails.getBookingRoomDetailsID());
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

    public boolean add(BookingRoomDetails bookingRoomDetails) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into KDLST.BookingRoomDetail (bookingRoomID, roomID) values(?,?)");
            prsm.setInt(1, bookingRoomDetails.getBookingRoom().getBookingRoomID());
            prsm.setInt(2, bookingRoomDetails.getRoom().getRoomID());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
