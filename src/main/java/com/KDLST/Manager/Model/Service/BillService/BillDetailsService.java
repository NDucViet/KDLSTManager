package com.KDLST.Manager.Model.Service.BillService;

import java.util.ArrayList;

import com.KDLST.Manager.Model.Entity.Bill.BillDetails;

public interface BillDetailsService {

    public ArrayList<BillDetails> getAll();

    public BillDetails getByID(int id);

    public boolean update(BillDetails billDetails);

    public boolean add(BillDetails billDetails);
}
