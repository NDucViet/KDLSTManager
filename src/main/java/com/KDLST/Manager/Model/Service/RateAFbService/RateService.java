package com.KDLST.Manager.Model.Service.RateAFbService;

import java.util.ArrayList;

import com.KDLST.Manager.Model.Entity.RateAFb.Rate;
import com.KDLST.Manager.Model.Entity.ServiceProject.Services;

public interface RateService {
public ArrayList<Rate> getAll();

    public boolean update(Rate rate);

    public boolean add(Rate rate);

    public float getScoreByService(Services service);

}
