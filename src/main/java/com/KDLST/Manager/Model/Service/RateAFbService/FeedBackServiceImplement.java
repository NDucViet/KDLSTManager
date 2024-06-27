package com.KDLST.Manager.Model.Service.RateAFbService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.KDLST.Manager.Model.Entity.RateAFb.FeedBack;
import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import com.KDLST.Manager.Model.Repository.RateAFbRepository.FeedBackRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedBackServiceImplement implements FeedBackService {
private ArrayList<FeedBack> feedBacks= new ArrayList<>();
    @Autowired
    private FeedBackRepository feedBackRepository=new FeedBackRepository();

    @Override
    public ArrayList<FeedBack> getAll() {
        this.feedBacks=feedBackRepository.getAll();
        return feedBacks;
    }

    @Override
    public boolean update(FeedBack feedBack) {
        if (feedBackRepository.update(feedBack)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(FeedBack feedBack) {
        if (feedBackRepository.add(feedBack)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (feedBackRepository.delete(id)) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<FeedBack> getByIdService(Services service) {
        return feedBackRepository.getByIdService(service);
    }
}
