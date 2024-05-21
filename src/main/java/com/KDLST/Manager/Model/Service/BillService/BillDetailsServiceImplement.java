package com.KDLST.Manager.Model.Service.BillService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.KDLST.Manager.Model.Entity.Bill.BillDetails;
import com.KDLST.Manager.Model.Repository.BillRepository.BillDetailsRepository;

public class BillDetailsServiceImplement implements BillDetailsService {
ArrayList<BillDetails> billDetailsList = new ArrayList<>();
    @Autowired
    BillDetailsRepository billDetailsRepository = new BillDetailsRepository();


    
    @Override
    public ArrayList<BillDetails> getAll(){
        this.billDetailsList = billDetailsRepository.getAll();
        return billDetailsList;
    }
    @Override
    public BillDetails getByID(int id){
        return billDetailsRepository.getById(id);
    }
    @Override
    public boolean update(BillDetails billDetails){
        if (billDetailsRepository.update(billDetails)) {
            return true;
        }
        return false;
    }
    @Override
    public boolean add(BillDetails billDetails){
        if (billDetailsRepository.add(billDetails)) {
            return true;
        }
        return false;
    }
}
