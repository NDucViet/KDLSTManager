package com.KDLST.Manager.Model.Service.BillService;

import java.util.ArrayList;
import java.util.Map;
import com.KDLST.Manager.Model.Entity.Bill.BillDetails;

public interface BillDetailsService {

    public ArrayList<BillDetails> getAll();

    public BillDetails getByID(int id);

    public Map<String, Double> getYearsRevenue();

    public Map<String, Double> getMonthlyRevenue(String year);

    public boolean update(BillDetails billDetails);

    public boolean add(BillDetails billDetails);
}
