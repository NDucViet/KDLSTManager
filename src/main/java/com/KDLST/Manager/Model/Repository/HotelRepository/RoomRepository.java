package com.KDLST.Manager.Model.Repository.HotelRepository;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.KDLST.Manager.Model.BaseConnection;
import java.sql.*;

import com.KDLST.Manager.Model.Entity.Hotel.Hotel;
import com.KDLST.Manager.Model.Entity.Hotel.Room;
import com.KDLST.Manager.Model.Entity.Hotel.RoomType;

import jakarta.el.ELException;

@Repository
public class RoomRepository {
    ArrayList<Room> roomList = new ArrayList<>();
    @Autowired
    HotelRepository hotelRepository = new HotelRepository();
    RoomTypeRepository roomTypeRepository = new RoomTypeRepository();

    public ArrayList<Room> getAll() {
        try {
            roomList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.Room");
            while (rs.next()) {
                int roomID = rs.getInt("roomID");
                Hotel hotel = hotelRepository.getById(rs.getInt("hotelID"));
                RoomType roomType = roomTypeRepository.getById(rs.getInt("roomTypeID"));
                boolean status = rs.getBoolean("status");
                Room room = new Room(roomID, hotel, roomType, status);
                roomList.add(room);
            }
            con.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        return roomList;
    }

    public Room getById(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.Room where KDLST.Room.roomID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find");
            }
            int roomID = rs.getInt("roomID");
            Hotel hotel = hotelRepository.getById(rs.getInt("hotelID"));
            RoomType roomType = roomTypeRepository.getById(rs.getInt("roomTypeID"));
            boolean status = rs.getBoolean("status");
            Room room = new Room(roomID, hotel, roomType, status);
            st.close();
            return room;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public ArrayList<Room> getByIdRoomType(int idRoomType) {
        ArrayList<Room> roList = new ArrayList<>();
        try {
            roList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.Room where KDLST.Room.roomTypeID = ?;");
            st.setInt(1, idRoomType);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int roomID = rs.getInt("roomID");
                Hotel hotel = hotelRepository.getById(rs.getInt("hotelID"));
                RoomType roomType = roomTypeRepository.getById(rs.getInt("roomTypeID"));
                boolean status = rs.getBoolean("status");
                Room room = new Room(roomID, hotel, roomType, status);
                roList.add(room);
            }
            st.close();
            return roList;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean update(Room room) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "update KDLST.Room set KDLST.Room.hotelID =?, KDLST.Room.roomTypeID=?, KDLST.Room.status =? where KDLST.Room.roomID =?");
            prsm.setInt(1, room.getHotel().getHotelID());
            prsm.setInt(2, room.getRoomType().getRoomTypeID());
            prsm.setBoolean(3, room.isStatus());
            prsm.setInt(4, room.getRoomID());
            int result = prsm.executeUpdate();
            System.out.println(result);
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
            // TODO: handle exception
        }
        return false;
    }

}
