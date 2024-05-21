package com.KDLST.Manager.Model.Service.ServiceProjectService;

import com.KDLST.Manager.Model.Entity.ServiceProject.ServiceType;
import com.KDLST.Manager.Model.Repository.ServiceProjectRepository.ServiceTypeRepository;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceTypeServiceImplement implements ServiceTypeService {

    ArrayList<ServiceType> serviceTypesList = new ArrayList<>();
    @Autowired
    ServiceTypeRepository serviceTypeRepository = new ServiceTypeRepository();

    @Override
    public ArrayList<ServiceType> getAll() {
        this.serviceTypesList = serviceTypeRepository.getAll();
        return serviceTypesList;
    }

    @Override
    public ServiceType getById(int id) {
        return serviceTypeRepository.getById(id);
    }

    @Override
    public boolean update(ServiceType serviceType) {
        if (serviceTypeRepository.update(serviceType)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(ServiceType serviceType) {
        if (serviceTypeRepository.add(serviceType)) {
            return true;
        }
        return false;
    }
}
