package com.KDLST.Manager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Date;

import com.KDLST.Manager.Model.Entity.User.User;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.KDLST.Manager.Model.Entity.Blog.Blog;
import com.KDLST.Manager.Model.Entity.ImageBlog.Image;
import com.KDLST.Manager.Model.Entity.RateAFb.Comment;
import com.KDLST.Manager.Model.Service.BlogService.BlogServiceImplement;
import com.KDLST.Manager.Model.Service.ImageBlogService.ImageServiceImplement;
import com.KDLST.Manager.Model.Service.RateAFbService.CommentServiceImplement;

import java.time.LocalDate;


@Controller
@RequestMapping(value = "/blog")
public class BlogController {

    private List<Image> iList = new ArrayList<>();
    private Set<Image> images = new HashSet<>();

    @Autowired
    private BlogServiceImplement blogServiceImplement = new BlogServiceImplement();
    private ImageServiceImplement imageServiceImplement = new ImageServiceImplement();
    private CommentServiceImplement commentServiceImplement = new CommentServiceImplement();


    
    @GetMapping("/getAll")
    public String getAll(Model model) {
        iList = imageServiceImplement.getAll();
        for (Image image : iList) {
            images.add(image);
        }
        model.addAttribute("images", images);
        return getPage(model, "1");
    }

    @GetMapping("/getAll/{page}")
    public String getPage(Model model, @PathVariable(value = "page") String currentPage) {
        ArrayList<Image> imageListt = new ArrayList<>(images);
        int imagePerPage = 6;
        int numPages = (int) Math.ceil((float) imageListt.size() / imagePerPage);
        int[] numPage = new int[numPages];
        for (int i = 0; i < numPages; i++) {
            numPage[i] = i + 1;
        }
        List<Image> imageList = new ArrayList<>();
        for (int i = (Integer.parseInt(currentPage) - 1) * imagePerPage; i < Integer.parseInt(currentPage)
                * imagePerPage; i++) {
            if (imageListt.size() <= i)
                break;
            imageList.add(imageListt.get(i));
        }
        model.addAttribute("imageList", imageList);
        model.addAttribute("numPage", numPage);
        model.addAttribute("currentPage", Integer.parseInt(currentPage));
        model.addAttribute("Previous", Integer.parseInt(currentPage) - 1);
        model.addAttribute("Next", Integer.parseInt(currentPage) + 1);
        return "User/blog";
    }

    @GetMapping("/getByID/{id}")
    public String getByID(@PathVariable(value = "id") int id, Model model) {
        iList = imageServiceImplement.getImagesByBlogTypeID(id);
        images.clear();
        for (Image image : iList) {
            images.add(image);
        }
        model.addAttribute("images", images);
        return getPage(model, "1");
    }

    @GetMapping("/showBlogDetail/{blogID}")
    public String showBlogDetail(Model model, @PathVariable("blogID") String blogID) {
        int blogIDInt = Integer.parseInt(blogID);
        ArrayList<Image> imageList = imageServiceImplement.getImagesByBlogID(blogIDInt);
        Image image1 = imageList.get(0);
        Image image2 = imageList.get(1);
        ArrayList<Comment> commentList = commentServiceImplement.getCommentByBlogID(blogIDInt);

        ArrayList<Image> imgList = imageServiceImplement.getImagesSortDate();
        Set<Image> images = new HashSet<>();
        for (Image image : imgList) {
            images.add(image);
            if (images.size() == 3) {
                break;
            }
        }


        int commentTotal = 0;
        if (commentList != null) {
            commentTotal = commentList.size();
        }
     

        model.addAttribute("images", images);
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
