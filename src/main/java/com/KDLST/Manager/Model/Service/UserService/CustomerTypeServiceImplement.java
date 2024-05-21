package com.KDLST.Manager.Model.Service.UserService;

import com.KDLST.Manager.Model.Entity.User.CustomerType;
import com.KDLST.Manager.Model.Repository.UserRepository.CustomerTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class CustomerTypeServiceImplement implements CustomerTypeService {
    private ArrayList<CustomerType> customerTypes = new ArrayList<>();
    @Autowired
    private CustomerTypeRepository customerTypeRepository=new CustomerTypeRepository();
    @Override
    public ArrayList<CustomerType> getAll() {
        this.customerTypes=customerTypeRepository.getAll();
        return customerTypes;
    }

    @Override
    public CustomerType getById(int id) {
        return customerTypeRepository.getById(id);
    }

    @Override
    public boolean update(CustomerType customerType) {
        if (customerTypeRepository.update(customerType)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(CustomerType customerType) {
        if (customerTypeRepository.add(customerType)) {
            return true;
        }
        return false;
    }
}
