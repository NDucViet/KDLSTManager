package com.KDLST.Manager.Model.Service.BillService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.KDLST.Manager.Model.Entity.Bill.Bill;
import com.KDLST.Manager.Model.Repository.BillRepository.BillRepository;

@Service
public class BillServiceImplement implements BillService {

    ArrayList<Bill> billList = new ArrayList<>();
    @Autowired
    BillRepository billRepository = new BillRepository();

    @Override
    public ArrayList<Bill> getAll() {
        this.billList = billRepository.getAll();
        return billList;
    }

    @Override
    public Bill getById(int id) {
        return billRepository.getById(id);
    }

    @Override
    public boolean update(Bill bill) {
        if (billRepository.update(bill)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(Bill bill) {
        if (billRepository.add(bill)) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Bill> getByIdUser(int id) {
        return billRepository.getByIdUser(id);
    }

}
