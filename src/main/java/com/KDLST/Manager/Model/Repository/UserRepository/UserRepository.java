package com.KDLST.Manager.Model.Repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.User.CustomerType;
import com.KDLST.Manager.Model.Entity.User.User;
import jakarta.el.ELException;

@Repository
public class UserRepository {
    ArrayList<User> userList = new ArrayList<>();
    @Autowired
    CustomerTypeRepository customerTypeRepository = new CustomerTypeRepository();

    // Tính sau
    public ArrayList<User> getAll() {
        try {
            userList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.User");
            while (rs.next()) {
                int userID = rs.getInt("userID");
                String email = rs.getString("email");
                String userName = rs.getString("name");
                Date dob = rs.getDate("DOB");
                String address = rs.getString("address");
                int gender = rs.getInt("gender");
                String phoneNumber = rs.getString("phoneNumber");
                String avatar = rs.getString("avatar");
                CustomerType CustomerType = customerTypeRepository.getById(rs.getInt("customerTypeID"));
                String role = rs.getString("role");
                String password = rs.getString("password");
                String nation = rs.getString("nation");
                String idCard = rs.getString("IDCard");
                Boolean status = rs.getBoolean("status");
                User user = new User(userID, CustomerType, userName, email, password, phoneNumber, idCard, address, dob,
                        gender, avatar, nation, role, status);
                userList.add(user);
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return userList;
    }

    public User getById(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.user where KDLST.user.userID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find userID");
            }
            int userID = rs.getInt("userID");
            String email = rs.getString("email");
            String userName = rs.getString("name");
            Date dob = rs.getDate("DOB");
            String address = rs.getString("address");
            int gender = rs.getInt("gender");
            String phoneNumber = rs.getString("phoneNumber");
            String avatar = rs.getString("avatar");
            CustomerType CustomerType = customerTypeRepository.getById(rs.getInt("customerTypeID"));
            String role = rs.getString("role");
            String password = rs.getString("password");
            String nation = rs.getString("nation");
            String idCard = rs.getString("IDCard");
            Boolean status = rs.getBoolean("status");
            User user = new User(userID, CustomerType, userName, email, password, phoneNumber, idCard, address, dob,
                    gender, avatar, nation, role, status);

            st.close();
            return user;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean update(User user) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "update KDLST.User set KDLST.User.email =?, KDLST.User.name=?, KDLST.User.DOB = ?, KDLST.User.address =?, KDLST.User.gender =?, KDLST.User.phoneNumber =?, KDLST.User.avatar =?, KDLST.User.customerTypeID =?, KDLST.User.status = ?, KDLST.User.IDCard =?, KDLST.User.nation =?, KDLST.User.password = ? , KDLST.User.role = ? where KDLST.User.userID =?");
            prsm.setString(1, user.getEmail());
            prsm.setString(2, user.getUsername());
            prsm.setDate(3, user.getDob());
            prsm.setString(4, user.getAddress());
            prsm.setInt(5, user.getGender());
            prsm.setString(6, user.getPhoneNumber());
            prsm.setString(7, user.getAvatar());
            prsm.setInt(8, user.getCustomerType().getIdCusType());
            prsm.setBoolean(9, user.getStatus());
            prsm.setString(10, user.getCardID());
            prsm.setString(12, user.getPassword());
            prsm.setString(11, user.getNation());
            prsm.setString(13, user.getRole());
            prsm.setInt(14, user.getIdUser());
            System.out.println(user.toString());
            int result = prsm.executeUpdate();
            System.out.println(result);
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean add(User user) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into KDLST.user (email, name, DOB, address, gender, phoneNumber, avatar, customerTypeID, status, IDCard, nation, password, role) values(?,?,?,?,?,?,?,?,?,?,?,?, ?)");
            prsm.setString(1, user.getEmail());
            prsm.setString(2, user.getUsername());
            prsm.setDate(3, user.getDob());
            prsm.setString(4, user.getAddress());
            prsm.setInt(5, user.getGender());
            prsm.setString(6, user.getPhoneNumber());
            prsm.setString(7, user.getAvatar());
            prsm.setInt(8, user.getCustomerType().getIdCusType());
            prsm.setBoolean(9, user.getStatus());
            prsm.setString(10, user.getCardID());
            prsm.setString(12, user.getPassword());
            prsm.setString(11, user.getNation());
            prsm.setString(13, user.getRole());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }


}
