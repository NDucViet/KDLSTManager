package com.KDLST.Manager.Model.Repository.ServiceProjectRepository;

import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.ServiceProject.ServiceType;
import jakarta.el.ELException;

import java.sql.*;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceTypeRepository {
    ArrayList<ServiceType> serviceTypesList = new ArrayList<>();

    public ArrayList<ServiceType> getAll() {
        try {
            serviceTypesList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.ServiceType");
            while (rs.next()) {
                int serviceTypeID = rs.getInt("serviceTypeID");
                String serviceName = rs.getString("serviceTypeName");
                ServiceType svtype = new ServiceType(serviceTypeID, serviceName);
                serviceTypesList.add(svtype);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return serviceTypesList;
    }

    public ServiceType getById(int serviceTypeId) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn
                    .prepareStatement("select * from KDLST.ServiceType where KDLST.ServiceType.serviceTypeID = ?;");
            st.setInt(1, serviceTypeId);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find");
            }
            int serviceTypeID = rs.getInt("serviceTypeID");
            String serviceName = rs.getString("serviceTypeName");
            ServiceType svtp = new ServiceType(serviceTypeID, serviceName);
            conn.close();
            return svtp;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean update(ServiceType serviceType) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "update KDLST.ServiceType set KDLST.ServiceType.serviceTypeName=?,where KDLST.ServiceType.serviceTypeID =?");
            prsm.setString(1, serviceType.getServiceName());
            int result = prsm.executeUpdate();
            System.out.println(result);
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean add(ServiceType ServiceType) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement("insert into KDLST.ServiceType (serviceTypeName) values(?)");
            prsm.setString(1, ServiceType.getServiceName());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
