package com.KDLST.Manager.Model.Service.ServiceProjectService;

import com.KDLST.Manager.Model.Entity.ServiceProject.ServiceType;

import java.util.ArrayList;

public interface ServiceTypeService {
    public ArrayList<ServiceType> getAll();

    public ServiceType getById(int id);

    public boolean update(ServiceType serviceType);

    public boolean add(ServiceType serviceType);
}
