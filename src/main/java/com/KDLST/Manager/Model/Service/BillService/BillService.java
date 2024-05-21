package com.KDLST.Manager.Model.Service.BillService;


import java.util.ArrayList;

import com.KDLST.Manager.Model.Entity.Bill.Bill;

public interface BillService {
    public ArrayList<Bill> getAll();

    public Bill getById(int id);

    public boolean update(Bill bill);

    public boolean add(Bill bill);
}
