package com.KDLST.Manager.Model.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Repository.UserRepository;

@Service
public class UserServiceImplement implements UserService {

    ArrayList<User> userList = new ArrayList<>();
    @Autowired
    UserRepository userRepository = new UserRepository();

    @Override
    public ArrayList<User> getAll() {
        this.userList = userRepository.getAll();
        return userList;
    }

    @Override
    public User getById(int id) {
        return userRepository.getById(id);
    }

    @Override
    public boolean update(User user) {
        if (userRepository.update(user)) {
            return true;
        }
        return false;
    }

    public User login(String email) {
        getAll();
        for (User user2 : userList) {
            if (user2.getEmail().equals(email)) {
                return user2;
            }
        }
        return null;
    }

    public boolean toLogin(User user) {
        getAll();
        for (User user2 : userList) {
            if (user2.getEmail().equals(user.getEmail()) && user2.getPassword().equals(user.getPassword())) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        UserServiceImplement userServiceImplement = new UserServiceImplement();
        System.out.println(userServiceImplement.toLogin(new User(0, null, null, "linhngo1052003.com", "password1", null,
                null, null, null, 0, null, null, null, null)));
    }

    @Override
    public boolean add(User user) {
        if (userRepository.add(user)) {
            return true;
        }
        return false;
    }

}
