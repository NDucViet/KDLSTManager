package com.KDLST.Manager.Model.Service.RateAFbService;

import java.util.ArrayList;

import com.KDLST.Manager.Model.Entity.RateAFb.Rate;

public interface RateService {
public ArrayList<Rate> getAll();

    public boolean update(Rate rate);

    public boolean add(Rate rate);

}
