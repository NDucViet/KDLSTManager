package com.KDLST.Manager.Model.Service.UserService;

import java.util.ArrayList;
import com.KDLST.Manager.Model.Entity.User.User;

public interface UserService {
    public ArrayList<User> getAll();

    public User getById(int id);

    public boolean update(User user);

    public boolean add(User user);

    public ArrayList<User> getAllCustomer();

    public ArrayList<User> getAllEmployee();
}
