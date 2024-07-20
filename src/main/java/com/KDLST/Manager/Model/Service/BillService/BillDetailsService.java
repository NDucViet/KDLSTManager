package com.KDLST.Manager.Model.Service.BillService;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.KDLST.Manager.Model.Entity.Bill.BillDetails;
@Service
public interface BillDetailsService {

    public ArrayList<BillDetails> getAll();

    public BillDetails getByID(int id);

    public Map<String, Double> getYearsRevenue();

    public Map<String, Double> getMonthlyRevenue(String year);

    public boolean update(BillDetails billDetails);

    public boolean add(BillDetails billDetails);

    public ArrayList<BillDetails> getByBillID(int id);

    public ArrayList<String> getYearRevenue() ;
}
