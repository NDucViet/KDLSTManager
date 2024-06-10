package com.KDLST.Manager.Model.Repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.User.CustomerType;

import jakarta.el.ELException;

@Repository
public class CustomerTypeRepository {
    private static ArrayList<CustomerType> customerTypeList = new ArrayList<>();

    public ArrayList<CustomerType> getAll() {
        try {
            customerTypeList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.CustomerType");
            while (rs.next()) {
                int customerID = rs.getInt("customerTypeID");
                String customerName = rs.getString("customerTypeName");
                String detail = rs.getString("description");
                CustomerType customerType = new CustomerType(customerID, customerName, detail);
                customerTypeList.add(customerType);
            }
            con.close();
        } catch (Exception e) {
        }
        return customerTypeList;
    }

    public CustomerType getById(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.CustomerType where KDLST.CustomerType.customerTypeID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find customerType");
            }
            int customerID = rs.getInt("customerTypeID");
            String customerName = rs.getString("customerTypeName");
            String detail = rs.getString("description");
            CustomerType customerType = new CustomerType(customerID, customerName, detail);
            st.close();
            return customerType;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean update(CustomerType customerType) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "update KDLST.CustomerType   set customerTypeName =?, description=? where customerTypeID =?");
            prsm.setString(1, customerType.getNameCusType());
            prsm.setString(2, customerType.getDetail());
            prsm.setInt(3, customerType.getIdCusType());

            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean add(CustomerType customerType) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into KDLST.CustomerType (customerTypeName, description) values(?,?)");
            prsm.setString(1, customerType.getNameCusType());
            prsm.setString(2, customerType.getDetail());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
        }
        return false;
    }
}
