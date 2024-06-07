package com.KDLST.Manager.Model.Service.ServiceProjectService;

import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import org.springframework.stereotype.Service;
import com.KDLST.Manager.Model.Repository.ServiceProjectRepository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;

@Service
public class ServiceServiceImplement implements ServiceService {
    private ArrayList<Services> servicesList = new ArrayList<>();
    @Autowired
    ServiceRepository serviceRepository = new ServiceRepository();

    @Override
    public ArrayList<Services> getAll() {
        this.servicesList = serviceRepository.getAll();
        return servicesList;
    }

    @Override
    public Services getById(int id) {
        return serviceRepository.getById(id);
    }

    @Override
    public boolean update(Services service) {
        if (serviceRepository.update(service)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(Services service) {
        if (serviceRepository.add(service)) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Services> getPageService(int index, int serviceTypeID) {
        ArrayList<Services> serviceList = serviceRepository.getPageService(index, serviceTypeID);
        if (!serviceList.isEmpty()) {
            return serviceList;
        }
        return null;
    }

    @Override
    public ArrayList<Services> getSerBySerTypeID(int serviceTypeID) {
        ArrayList<Services> serviceList = serviceRepository.getSerBySerTypeID(serviceTypeID);
        if (!serviceList.isEmpty()) {
            return serviceList;
        }
        return null;
    }

    public static void main(String[] args) {
        ServiceServiceImplement serviceServiceImplement = new ServiceServiceImplement();
        System.out.println(serviceServiceImplement.getPageService(1, 1).size());
        System.out.println(serviceServiceImplement.getSerBySerTypeID(1).size());
    }
}
