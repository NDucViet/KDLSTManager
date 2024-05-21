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
    private ArrayList<Room> roomList;
    @Autowired
    private HotelRepository hotelRepository;
    private RoomTypeRepository roomTypeRepository;

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
                double acreage = rs.getDouble("acreage");
                int maxPeople = rs.getInt("maxOfPeople");
                double price = rs.getDouble("price");
                String image = rs.getString("image");
                String amenities = rs.getString("amenities");
                boolean status = rs.getBoolean("status");
                Room room = new Room(roomID, hotel, roomType, acreage, maxPeople, price, image, amenities, status);
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
            double acreage = rs.getDouble("acreage");
            int maxPeople = rs.getInt("maxOfPeople");
            double price = rs.getDouble("price");
            String image = rs.getString("image");
            String amenities = rs.getString("amenities");
            boolean status = rs.getBoolean("status");
            Room room = new Room(roomID, hotel, roomType, acreage, maxPeople, price, image, amenities, status);
            st.close();
            return room;
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
                    "update KDLST.Room set KDLST.Room.hotelID =?, KDLST.Room.roomTypeID=?, KDLST.Room.quantity = ?, KDLST.Room.maxOfPeople =?, KDLST.Room.price =?, KDLST.Room.image =?, KDLST.Room.amenities =?, KDLST.Room.status =? where KDLST.Room.roomID =?");
            prsm.setInt(1, room.getHotel().getHotelID());
            prsm.setInt(2, room.getRoomType().getRoomTypeID());
            prsm.setDouble(3, room.getAcreage());
            prsm.setInt(4, room.getMaxPeople());
            prsm.setDouble(5, room.getPrice());
            prsm.setString(6, room.getImage());
            prsm.setString(7, room.getAmenities());
            prsm.setBoolean(8, room.isStatus());
            prsm.setInt(9, room.getRoomID());
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

    public static void main(String[] args) {
        RoomRepository roomRepository = new RoomRepository();
        System.out.println(roomRepository.getAll());
    }
}
