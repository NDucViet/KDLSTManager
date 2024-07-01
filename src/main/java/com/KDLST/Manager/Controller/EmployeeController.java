package com.KDLST.Manager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.LocalDate;
import java.io.IOException;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Entity.Blog.Blog;
import com.KDLST.Manager.Model.Entity.Blog.BlogType;
import com.KDLST.Manager.Model.Entity.ImageBlog.Image;
import com.KDLST.Manager.Model.Service.BlogService.BlogServiceImplement;
import com.KDLST.Manager.Model.Service.BlogService.BlogTypeServiceImplement;
import com.KDLST.Manager.Model.Service.ImageBlogService.ImageServiceImplement;
import com.KDLST.Manager.Model.Service.UploadFile.StorageService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;



@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {
 @Autowired
    private BlogServiceImplement blogServiceImplement = new BlogServiceImplement();
    private ImageServiceImplement imageServiceImplement = new ImageServiceImplement();
    private BlogTypeServiceImplement blogTypeServiceImplement = new BlogTypeServiceImplement();
    @Autowired
    private StorageService storageService;

     @GetMapping("/")
    public String index(Model model){
        return "Employee/index";
    }

    @GetMapping("/getAllCustomer")
    public String getAllCustomers(Model model){
        return "Employee/customer";
    }
   @GetMapping("/addBlog")
    public String showBlogAdd(Model model, Blog blog, Image image1, Image image2) {
        model.addAttribute("blog", blog);
        model.addAttribute("image1", image1);
        model.addAttribute("image2", image2);
        return "User/blogAdd";
    }

    @PostMapping("/addBlog/action")
    public String addBlog(Model model,
            @ModelAttribute("blog") Blog blog1,
            @ModelAttribute("image1") Image image1,
            @ModelAttribute("image2") Image image2,
            @RequestParam("imageUrl1") MultipartFile imageUrl1,
            @RequestParam("imageUrl2") MultipartFile imageUrl2,
            @RequestParam(value = "blogTypeID") int blogTypeID,
            HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        User user = new User(3, null, null, null, null, null, null, null, null, blogTypeID, null, null, null, null);
        BlogType blogType = blogTypeServiceImplement.getById(blogTypeID);
        blog1.setUser(user);
        blog1.setBlogType(blogType);
        // set time
        LocalDate today = LocalDate.now();
        Date sqlDate = Date.valueOf(today);
        blog1.setDateTimeEdit(sqlDate);
        blog1.setStatus(true);
        // add blog1
        blogServiceImplement.add(blog1);
        Blog blogID = blogServiceImplement.getIdLastest();
        blog1.setBlogID(blogID.getBlogID());
        // image1
        String imageUrl1Filename = "";
        this.storageService.store(imageUrl1);
        imageUrl1Filename = imageUrl1.getOriginalFilename();
        image1.setImageUrl(imageUrl1Filename);
        image1.setBlog(blog1);
        imageServiceImplement.add(image1);

        // image2
        String imageUrl2Filename = "";
        this.storageService.store(imageUrl2);
        imageUrl2Filename = imageUrl2.getOriginalFilename();
        image2.setImageUrl(imageUrl2Filename);
        image2.setBlog(blog1);
        imageServiceImplement.add(image2);
        return "User/blogAdd";
    }


    

}

