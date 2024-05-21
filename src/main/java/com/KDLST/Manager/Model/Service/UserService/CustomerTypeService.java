package com.KDLST.Manager.Model.Service.UserService;

import java.util.ArrayList;

import com.KDLST.Manager.Model.Entity.User.CustomerType;

public interface CustomerTypeService {
    public ArrayList<CustomerType> getAll();

    public CustomerType getById(int id);

    public boolean update(CustomerType customerType);

    public boolean add(CustomerType customerType);
}
