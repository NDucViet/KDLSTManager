package com.KDLST.Manager.Model.Service.RateAFbService;

import com.KDLST.Manager.Model.Entity.RateAFb.Rate;
import com.KDLST.Manager.Model.Repository.RateAFbRepository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RateServiceImplement implements RateService {
    private ArrayList<Rate> rates= new ArrayList<>();
    @Autowired
    private RateRepository rateRepository =new RateRepository();


    @Override
    public ArrayList<Rate> getAll() {
        this.rates=rateRepository.getAll();
        return rates;
    }

    @Override
    public boolean update(Rate rate) {
        if (rateRepository.update(rate)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(Rate rate) {
        if (rateRepository.add(rate)) {
            return true;
        }
        return false;
    }
}
