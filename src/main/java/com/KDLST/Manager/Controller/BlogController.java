package com.KDLST.Manager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Date;

import com.KDLST.Manager.Model.Entity.User.User;

import java.util.ArrayList;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.KDLST.Manager.Model.Entity.Blog.Blog;
import com.KDLST.Manager.Model.Entity.Blog.BlogType;
import com.KDLST.Manager.Model.Entity.ImageBlog.Image;
import com.KDLST.Manager.Model.Entity.RateAFb.Comment;
import com.KDLST.Manager.Model.Service.BlogService.BlogServiceImplement;
import com.KDLST.Manager.Model.Service.BlogService.BlogTypeServiceImplement;
import com.KDLST.Manager.Model.Service.ImageBlogService.ImageServiceImplement;
import com.KDLST.Manager.Model.Service.RateAFbService.CommentServiceImplement;
import com.KDLST.Manager.Model.Service.UploadFile.StorageService;

import jakarta.servlet.ServletException;
import java.time.LocalDate;

import java.io.IOException;

@Controller
@RequestMapping(value = "/blog")
public class BlogController {
    @Autowired
    private BlogServiceImplement blogServiceImplement = new BlogServiceImplement();
    private ImageServiceImplement imageServiceImplement = new ImageServiceImplement();
    private BlogTypeServiceImplement blogTypeServiceImplement = new BlogTypeServiceImplement();
    private CommentServiceImplement commentServiceImplement = new CommentServiceImplement();
    @Autowired
    private StorageService storageService;

    @GetMapping("/blog")
    public String blog() {
        return "Blog/blog";
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
        System.out.println(blogTypeID + "blogTypeID");
        blog1.setUser(user);
        blog1.setBlogType(blogType);
        // set time
        LocalDate today = LocalDate.now();
        Date sqlDate = Date.valueOf(today);
        blog1.setDateTimeEdit(sqlDate);
        // add blog1
        blogServiceImplement.add(blog1);

        int numOfBlog = blogServiceImplement.getAll().size();
        blog1.setBlogID(numOfBlog);
        System.out.println(numOfBlog);
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

    @GetMapping("/showBlog")
    public String showBlog(Model model, @RequestParam(name = "index", defaultValue = "1") int index,
            @RequestParam(name = "blogTypeID", defaultValue = "1") int blogTypeID, HttpServletRequest request) {
        ArrayList<Image> imageList = imageServiceImplement.getPageImage(index, blogTypeID);
        int endPage=1;
        if (imageList != null) {
            int count = imageList.size();
             endPage = count / 6;
            if (count % 6 != 0) {
                endPage++;
            }
        }

        model.addAttribute("imageList", imageList);
        model.addAttribute("currentPage", index);
        model.addAttribute("endPage", endPage);
        return "User/blog";
    }

    @GetMapping("/showBlogDetail/{blogID}")
    public String showBlogDetail(Model model, @PathVariable("blogID") String blogID) {
        int blogIDInt = Integer.parseInt(blogID);
        System.out.println(blogIDInt);
        ArrayList<Image> imageList = imageServiceImplement.getImagesByBlogID(blogIDInt);
        ArrayList<Comment> commentList = commentServiceImplement.getCommentByBlogID(blogIDInt);
        int commentTotal = 0;
        if (commentList != null) {
            commentTotal = commentList.size();
        }
        Image image1 = null;
        Image image2 = null;
        if (imageList != null) {
            for (Image image : imageList) {
                if (image1 == null) {
                    image1 = image;
                } else {
                    image2 = image;
                }
            }
        }

        model.addAttribute("commentTotal", commentTotal);
        model.addAttribute("image1", image1);
        model.addAttribute("image2", image2);
        model.addAttribute("commentList", commentList);
        model.addAttribute("blogID", blogID);
        return "User/blog-single";
    }

    @PostMapping("/submitComment")
    public String submitComment(@RequestParam(name = "blogID") int blogID,
            @RequestParam(name = "content") String content, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        Blog blog = blogServiceImplement.getById(blogID);

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setBlog(blog);
        comment.setContent(content);
        LocalDate today = LocalDate.now();
        Date sqlDate = Date.valueOf(today);
        comment.setCommentDate(sqlDate);
        commentServiceImplement.add(comment);
        return "redirect:/blog/showBlogDetail/" + blogID;
    }
}
