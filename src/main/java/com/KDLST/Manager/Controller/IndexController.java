package com.KDLST.Manager.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.KDLST.Manager.Model.Entity.Hotel.RoomType;
import com.KDLST.Manager.Model.Entity.ImageBlog.Image;
import com.KDLST.Manager.Model.Entity.RateAFb.FeedBack;
import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import com.KDLST.Manager.Model.Service.BlogService.BlogService;
import com.KDLST.Manager.Model.Service.BlogService.BlogServiceImplement;
import com.KDLST.Manager.Model.Service.HotelService.RoomTypeService;
import com.KDLST.Manager.Model.Service.HotelService.RoomTypeServiceImplement;
import com.KDLST.Manager.Model.Service.ImageBlogService.ImageService;
import com.KDLST.Manager.Model.Service.ImageBlogService.ImageServiceImplement;
import com.KDLST.Manager.Model.Service.RateAFbService.FeedBackService;
import com.KDLST.Manager.Model.Service.RateAFbService.FeedBackServiceImplement;
import com.KDLST.Manager.Model.Service.RateAFbService.RateService;
import com.KDLST.Manager.Model.Service.RateAFbService.RateServiceImplement;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceService;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceServiceImplement;

@Controller
@RequestMapping({ "" })
public class IndexController {

    @Autowired
    ServiceService service = new ServiceServiceImplement();
    BlogService blogService = new BlogServiceImplement();
    ImageService imageService = new ImageServiceImplement();
    RoomTypeService roomTypeService = new RoomTypeServiceImplement();
    FeedBackService feedBackService = new FeedBackServiceImplement();
    RateService rateService = new RateServiceImplement();

    // service, blog
    @GetMapping("")
    public String index(Model model) {
        // Populate serviceListAll
        Map<Services, ArrayList<FeedBack>> serviceListAll = new LinkedHashMap<>();

        // Copy top 3 entries from serviceListAll to serviceListRecommend
        Map<Services, ArrayList<FeedBack>> serviceListRecommend = new LinkedHashMap<>();
        int count = 0;

        for (Services service : service.getAll()) {
            ArrayList<FeedBack> feedBacks = feedBackService.getByIdService(service);
            Collections.reverse(feedBacks);
            serviceListAll.put(service, feedBacks);
            count++;
            if (count == 3) {
                serviceListRecommend.putAll(serviceListAll);
            }
        }

        // blog
        ArrayList<Image> imgList = imageService.getImagesSortDate();
        Set<Image> images = new HashSet<>();
        for (Image image : imgList) {
            images.add(image);
            if (images.size() == 3) {
                break;
            }
        }

        // Get roomTypeList
        ArrayList<RoomType> roomTypeList = roomTypeService.getAll();

        // Add attributes to model
        model.addAttribute("roomTypeList", roomTypeList);
        model.addAttribute("sList", serviceListRecommend);
        model.addAttribute("sListAll", serviceListAll);
        model.addAttribute("blogList", images);
        return "User/index";
    }

    @GetMapping("/about")
    public String about() {
        return "User/about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "User/contact";
    }

}
