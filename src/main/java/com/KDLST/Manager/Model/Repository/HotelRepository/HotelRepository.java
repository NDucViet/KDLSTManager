package com.KDLST.Manager.Model.Repository.HotelRepository;

import java.sql.*;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.Hotel.Hotel;
import com.KDLST.Manager.Model.Entity.ServiceProject.ServiceType;
import com.KDLST.Manager.Model.Repository.ServiceProjectRepository.ServiceTypeRepository;

import jakarta.el.ELException;

@Repository
public class HotelRepository {
    private ArrayList<Hotel> hotelList;

    @Autowired
    ServiceTypeRepository serviceTypeRepository = new ServiceTypeRepository();

    public ArrayList<Hotel> getAll() {
        try {
            hotelList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.Hotel");
            while (rs.next()) {
                int hotelID = rs.getInt("hotelID");
                ServiceType serviceType = serviceTypeRepository.getById(rs.getInt("serviceTypeID"));
                String hotelName = rs.getString("hotelName");
                Hotel hotel = new Hotel(hotelID, serviceType, hotelName);
                hotelList.add(hotel);
            }
            con.close();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        return hotelList;
    }

    public Hotel getById(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.Hotel where KDLST.Hotel.hotelID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find");
            }
            int hotelID = rs.getInt("hotelID");
            ServiceType serviceType = serviceTypeRepository.getById(rs.getInt("serviceTypeID"));
            String hotelName = rs.getString("hotelName");
            Hotel hotel = new Hotel(hotelID, serviceType, hotelName);
            st.close();
            return hotel;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
