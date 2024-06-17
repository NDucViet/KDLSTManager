package com.KDLST.Manager.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.KDLST.Manager.Model.Entity.Hotel.RoomType;
import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import com.KDLST.Manager.Model.Service.BlogService.BlogService;
import com.KDLST.Manager.Model.Service.BlogService.BlogServiceImplement;
import com.KDLST.Manager.Model.Service.HotelService.RoomTypeService;
import com.KDLST.Manager.Model.Service.HotelService.RoomTypeServiceImplement;
import com.KDLST.Manager.Model.Service.ImageBlogService.ImageService;
import com.KDLST.Manager.Model.Service.ImageBlogService.ImageServiceImplement;
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

    // service, blog
    @GetMapping("")
    public String index(Model model) {
        ArrayList<Services> sList = service.getAll();
        ArrayList<Services> sLists = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            sLists.add(sList.get(i));
        }

        ArrayList<RoomType> roomTypeList = roomTypeService.getAll();
        model.addAttribute("roomTypeList", roomTypeList);
        model.addAttribute("sList", sLists);
        model.addAttribute("sListAll", sList);
        return "User/index";
    }

}
