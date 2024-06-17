package com.KDLST.Manager.Model.Repository.HotelRepository;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;
import java.sql.*;
import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.Hotel.RoomType;

import jakarta.el.ELException;

@Repository
public class RoomTypeRepository {
    ArrayList<RoomType> roomTypeList = new ArrayList<>();

    public ArrayList<RoomType> getAll() {
        try {
            roomTypeList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.RoomType");
            while (rs.next()) {
                int id = rs.getInt("roomTypeID");
                String name = rs.getString("roomTypeName");
                double price = rs.getDouble("price");
                String images = rs.getString("image");
                String details = rs.getString("details");
                int maxPeople = rs.getInt("maxOfPeople");
                int quantity = rs.getInt("quantity");
                RoomType roomType = new RoomType(id, name, price, images, details, maxPeople, quantity);
                roomTypeList.add(roomType);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return roomTypeList;
    }

    public RoomType getById(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.RoomType where KDLST.RoomType.roomTypeID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find");
            }
            String name = rs.getString("roomTypeName");
            double price = rs.getDouble("price");
            String images = rs.getString("image");
            String details = rs.getString("details");
            int maxPeople = rs.getInt("maxOfPeople");
            int quantity = rs.getInt("quantity");
            RoomType roomType = new RoomType(id, name, price, images, details, maxPeople, quantity);
            conn.close();
            return roomType;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

}
