package com.KDLST.Manager.Model.Service.ServiceProjectService;

import java.util.ArrayList;

import com.KDLST.Manager.Model.Entity.ServiceProject.Services;

public interface ServiceService {
public ArrayList<Services> getAll();

    public Services getById(int id);

    public boolean update(Services service);

    public boolean add(Services service);

    public ArrayList<Services> getPageImage(int index, int serviceTypeID);

}
