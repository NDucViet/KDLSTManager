package com.KDLST.Manager.Model.Repository.ServiceProjectRepository;

import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.ServiceProject.ServiceType;
import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import jakarta.el.ELException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class ServiceRepository {
    private ArrayList<Services> serviceList = new ArrayList<>();
    @Autowired
    private ServiceTypeRepository serviceTypeRepositories = new ServiceTypeRepository();

    public ArrayList<Services> getAll() {
        try {
            serviceList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.Service");
            while (rs.next()) {
                int serviceID = rs.getInt("serviceID");
                ServiceType serviceType = serviceTypeRepositories.getById(rs.getInt("serviceTypeID"));
                String description = rs.getString("description");
                String image = rs.getString("image");
                java.sql.Date dateTimeEdit = rs.getDate("dateTimeEdit");
                String serviceName = rs.getString("serviceName");
                Services sv = new Services(serviceID, serviceType, description, image, dateTimeEdit, serviceName);
                serviceList.add(sv);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return serviceList;
    }

    public Services getById(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.Service where KDLST.Service.serviceID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find Service");
            }
            int serviceID = rs.getInt("serviceID");
            ServiceType serviceType = serviceTypeRepositories.getById(rs.getInt("serviceTypeID"));
            String description = rs.getString("description");
            String image = rs.getString("image");
            java.sql.Date dateTimeEdit = rs.getDate("dateTimeEdit");
            String serviceName = rs.getString("serviceName");
            Services sv = new Services(serviceID, serviceType, description, image, dateTimeEdit, serviceName);
            conn.close();
            return sv;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean update(Services service) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "update KDLST.Service   set serviceTypeID =?, description=?,image=?,dateTimeEdit=? serviceName =? where serviceID =?");
            prsm.setInt(1, service.getServiceTypeID().getServiceTypeID());
            prsm.setString(2, service.getDescription());
            prsm.setString(3, service.getImage());
            prsm.setDate(4, service.getDateTimeEdit());
            prsm.setString(5, service.getServiceName());
            prsm.setInt(6, service.getServiceID());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean add(Services service) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into KDLST.Service (serviceTypeID, description,image,dateTimeEdit,serviceName) values(?,?,?,?,?)");
            prsm.setInt(1, service.getServiceTypeID().getServiceTypeID());
            prsm.setString(2, service.getDescription());
            prsm.setString(3, service.getImage());
            prsm.setDate(4, service.getDateTimeEdit());
            prsm.setString(5, service.getServiceName());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public ArrayList<Services> getPageService(int index, int serviceTypeID) {
        try {
            serviceList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement stsm = con.prepareStatement(
                    "SELECT * FROM KDLST.Service WHERE Service.serviceTypeID = ? ORDER BY Service.serviceID LIMIT 6 OFFSET ?");
            stsm.setInt(1, serviceTypeID);
            stsm.setInt(2, (index - 1) * 6);
            ResultSet rs = stsm.executeQuery();
            while (rs.next()) {
                int serviceID = rs.getInt("serviceID");
                ServiceType serviceType = serviceTypeRepositories.getById(rs.getInt("serviceTypeID"));
                String description = rs.getString("description");
                String image = rs.getString("image");
                java.sql.Date dateTimeEdit = rs.getDate("dateTimeEdit");
                String serviceName = rs.getString("serviceName");
                Services sv = new Services(serviceID, serviceType, description, image, dateTimeEdit, serviceName);
                serviceList.add(sv);
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return serviceList;
    }

    public ArrayList<Services> getSerBySerTypeID(int serviceTypeID) {
        try {
            serviceList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement stsm = con.prepareStatement(
                    "select * from KDLST.Service where serviceTypeID = ?");
            stsm.setInt(1, serviceTypeID);
            ResultSet rs = stsm.executeQuery();
            while (rs.next()) {
                int serviceID = rs.getInt("serviceID");
                ServiceType serviceType = serviceTypeRepositories.getById(rs.getInt("serviceTypeID"));
                String description = rs.getString("description");
                String image = rs.getString("image");
                java.sql.Date dateTimeEdit = rs.getDate("dateTimeEdit");
                String serviceName = rs.getString("serviceName");
                Services sv = new Services(serviceID, serviceType, description, image, dateTimeEdit, serviceName);
                serviceList.add(sv);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return serviceList;
    }

}
