package com.KDLST.Manager.Model.Service.ServiceProjectService;

import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import org.springframework.stereotype.Service;
import com.KDLST.Manager.Model.Repository.ServiceProjectRepository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Predicate;

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

    private static Predicate<Services> filterByName(String keyword) {
        return p -> p.getServiceName().toLowerCase().contains(keyword.toLowerCase());
    }

    private static Predicate<Services> filterByType(String keyword) {
        return p -> p.getServiceTypeID().getServiceName().toLowerCase().contains(keyword.toLowerCase());
    }

    private ArrayList<Services> filterServices(String keyword, Predicate<Services> predicate) {
        ArrayList<Services> filteredServices = new ArrayList<>();
        getAll();
        for (Services service : servicesList) {
            if (predicate.test(service)) {
                filteredServices.add(service);
            }
        }
        return filteredServices;
    }

    @Override
    public ArrayList<Services> searchService(String keyword) {
        HashSet<Services> services = new HashSet<>();
        services.addAll(filterServices(keyword, filterByName(keyword)));
        services.addAll(filterServices(keyword, filterByType(keyword)));
        return new ArrayList<>(services);
    }
}
