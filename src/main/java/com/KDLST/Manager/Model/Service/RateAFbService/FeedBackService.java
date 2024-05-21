package com.KDLST.Manager.Model.Service.RateAFbService;

import java.util.ArrayList;

import com.KDLST.Manager.Model.Entity.RateAFb.FeedBack;

public interface FeedBackService {
    public ArrayList<FeedBack> getAll();

    public boolean update(FeedBack feedBack);

    public boolean add(FeedBack feedBack);

}
