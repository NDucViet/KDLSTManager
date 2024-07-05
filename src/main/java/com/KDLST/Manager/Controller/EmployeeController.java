package com.KDLST.Manager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.LocalDate;
import java.io.IOException;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Entity.Bill.Bill;
import com.KDLST.Manager.Model.Entity.Bill.BillDetails;
import com.KDLST.Manager.Model.Entity.Blog.Blog;
import com.KDLST.Manager.Model.Entity.Blog.BlogType;
import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoom;
import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoomDetails;
import com.KDLST.Manager.Model.Entity.Hotel.Room;
import com.KDLST.Manager.Model.Entity.Hotel.RoomType;
import com.KDLST.Manager.Model.Entity.ImageBlog.Image;
import com.KDLST.Manager.Model.Entity.RateAFb.Comment;
import com.KDLST.Manager.Model.Entity.RateAFb.FeedBack;
import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import com.KDLST.Manager.Model.Entity.Ticket.Ticket;
import com.KDLST.Manager.Model.Service.BillService.BillDetailsService;
import com.KDLST.Manager.Model.Service.BillService.BillDetailsServiceImplement;
import com.KDLST.Manager.Model.Service.BillService.BillService;
import com.KDLST.Manager.Model.Service.BillService.BillServiceImplement;
import com.KDLST.Manager.Model.Service.BlogService.BlogServiceImplement;
import com.KDLST.Manager.Model.Service.BlogService.BlogTypeServiceImplement;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomDetailsService;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomDetailsServiceImplement;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomService;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomServiceImplement;
import com.KDLST.Manager.Model.Service.HotelService.RoomService;
import com.KDLST.Manager.Model.Service.HotelService.RoomServiceImplement;
import com.KDLST.Manager.Model.Service.HotelService.RoomTypeService;
import com.KDLST.Manager.Model.Service.HotelService.RoomTypeServiceImplement;
import com.KDLST.Manager.Model.Service.ImageBlogService.ImageService;
import com.KDLST.Manager.Model.Service.ImageBlogService.ImageServiceImplement;
import com.KDLST.Manager.Model.Service.RateAFbService.CommentService;
import com.KDLST.Manager.Model.Service.RateAFbService.CommentServiceImplement;
import com.KDLST.Manager.Model.Service.RateAFbService.FeedBackService;
import com.KDLST.Manager.Model.Service.RateAFbService.FeedBackServiceImplement;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceService;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceServiceImplement;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceTypeService;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceTypeServiceImplement;
import com.KDLST.Manager.Model.Service.TicketService.TicketService;
import com.KDLST.Manager.Model.Service.TicketService.TicketServiceImplement;
import com.KDLST.Manager.Model.Service.UploadFile.StorageService;
import com.KDLST.Manager.Model.Service.UserService.UserService;
import com.KDLST.Manager.Model.Service.UserService.UserServiceImplement;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import java.util.*;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {
    ArrayList<BillDetails> billDetails = new ArrayList<>();
    ArrayList<BookingRoomDetails> bookingRoomDetails = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Bill> bills = new ArrayList<>();
    ArrayList<BookingRoom> bookingRooms = new ArrayList<>();
    ArrayList<User> cList = new ArrayList<>();
    ArrayList<User> eList = new ArrayList<>();
 @Autowired
    private BlogServiceImplement blogServiceImplement = new BlogServiceImplement();
    private ImageServiceImplement imageServiceImplement = new ImageServiceImplement();
    private BlogTypeServiceImplement blogTypeServiceImplement = new BlogTypeServiceImplement();
    @Autowired
    private StorageService storageService;
    
    @Autowired
    ServiceService serviceService = new ServiceServiceImplement();
    ServiceTypeService serviceTypeService = new ServiceTypeServiceImplement();
    BillDetailsService billDetailsService = new BillDetailsServiceImplement();
    BookingRoomDetailsService bookingRoomDetailsService = new BookingRoomDetailsServiceImplement();
    TicketService ticketService = new TicketServiceImplement();
    UserService userService = new UserServiceImplement();
    BookingRoomService bookingRoomService = new BookingRoomServiceImplement();
    BillService billService = new BillServiceImplement();
    RoomTypeService roomTypeService = new RoomTypeServiceImplement();
    RoomService roomService = new RoomServiceImplement();
    ImageService imageService = new ImageServiceImplement();
    FeedBackService feedBackService = new FeedBackServiceImplement();
    CommentService commentService = new CommentServiceImplement();

     @GetMapping("/")
    public String index(Model model) throws JsonProcessingException {
        eList.clear();
        cList.clear();
      
        bookingRooms = bookingRoomService.getAll();

        users = userService.getAll();

        bills = billService.getAll();
        // employee, customer
        int customer = 0;
        int employee = 0;

        for (User user : users) {
            if (user.getRole().equals("CUSTOMER")) {
                customer += 1;
                if (cList.size() < 7) {
                    cList.add(user);
                }
            } else if (user.getRole().equals("EMPLOYEE")) {
                employee += 1;
                if (eList.size() < 7) {
                    eList.add(user);
                }
            }
        }

        // service
        ArrayList<Services> sList = serviceService.getAll();
        ArrayList<Services> sLists = new ArrayList<>();
        for (Services s : sList) {
            if (sLists.size() < 7) {
                sLists.add(s);
            }
        }

        // ticket
        ArrayList<Ticket> ticketList = ticketService.getAll();
        ArrayList<Ticket> ticketLists = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            if (ticketLists.size() < 7) {
                ticketLists.add(ticket);
            }
        }

        // blog
        ArrayList<Image> imgList = imageService.getAll();
        Set<Image> images = new HashSet<>();
        for (Image image : imgList) {
            images.add(image);
            if (images.size() == 6) {
                break;
            }
        }

        // room
        ArrayList<RoomType> rList = roomTypeService.getAll();

        // feedback
        ArrayList<FeedBack> fList = feedBackService.getAll();
        ArrayList<FeedBack> fLists = new ArrayList<>();
        for (FeedBack feedback : fList) {
            if (fLists.size() < 7) {
                fLists.add(feedback);
            }
        }

        // comment
        ArrayList<Comment> comList = commentService.getAll();
        ArrayList<Comment> comListLists = new ArrayList<>();
        for (Comment comment : comList) {
            if (comListLists.size() < 7) {
                comListLists.add(comment);
            }
        }
        model.addAttribute("service", serviceService.getAll().size());
        model.addAttribute("order", bills.size() + bookingRooms.size());
        model.addAttribute("customer", customer);
        model.addAttribute("employee", employee);
        model.addAttribute("cList", cList);
        model.addAttribute("eList", eList);
        model.addAttribute("serviceList", sLists);
        model.addAttribute("feedbackList", fLists);
        model.addAttribute("commentList", comListLists);
        model.addAttribute("roomTypeList", rList);
        model.addAttribute("ticketList", ticketLists);
        model.addAttribute("blogList", images);
        return "Employee/index";
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

    
    // customer
    @GetMapping("/getAllCustomer")
    public String getAllCustomer(Model model) {
        cList.clear();
        cList = userService.getAllCustomer();
        return getPageCustomer(model, "1");
    }

    @GetMapping("/getAllCustomer/{page}")
    public String getPageCustomer(Model model, @PathVariable(value = "page") String currentPage) {
        cList.clear();
        ArrayList<User> customerList = new ArrayList<>();
        cList = userService.getAllCustomer();
        int customerPage = 10;
        int numPages = (int) Math.ceil((float) cList.size() / customerPage);
        int[] numPage = new int[numPages];
        for (int i = 0; i < numPages; i++) {
            numPage[i] = i + 1;
        }
        for (int i = (Integer.parseInt(currentPage) - 1) * customerPage; i < Integer.parseInt(currentPage)
                * customerPage; i++) {
            if (cList.size() <= i)
                break;
            customerList.add(cList.get(i));
        }
        model.addAttribute("customerList", customerList);
        model.addAttribute("numPage", numPage);
        model.addAttribute("currentPage", Integer.parseInt(currentPage));
        model.addAttribute("Previous", Integer.parseInt(currentPage) - 1);
        model.addAttribute("Next", Integer.parseInt(currentPage) + 1);
        return "Employee/customer";
    }

    // service
    @GetMapping("/getAllService")
    public String getAllServices(Model model) {
        ArrayList<Services> serList = serviceService.getAll();
        model.addAttribute("serList", serList);
        return getPageService(model, "1", serList);
    }

    @GetMapping("/getAllService/{page}")
    public String getPageService(Model model, @PathVariable(value = "page") String currentPage,
            @ModelAttribute("serList") ArrayList<Services> serList) {
        ArrayList<Services> serviceList = new ArrayList<>();
        int page = 10;
        int numPages = (int) Math.ceil((float) serList.size() / page);
        int[] numPage = new int[numPages];
        for (int i = 0; i < numPages; i++) {
            numPage[i] = i + 1;
        }

        for (int i = (Integer.parseInt(currentPage) - 1) * page; i < Integer.parseInt(currentPage)
                * page; i++) {
            if (serList.size() <= i)
                break;
            serviceList.add(serList.get(i));
        }
        model.addAttribute("serviceList", serviceList);
        model.addAttribute("numPage", numPage);
        model.addAttribute("currentPage", Integer.parseInt(currentPage));
        model.addAttribute("Previous", Integer.parseInt(currentPage) - 1);
        model.addAttribute("Next", Integer.parseInt(currentPage) + 1);
        return "Employee/service";
    }

    // ticket
    @GetMapping("/getAllTicket")
    public String getAllTicket(Model model) {
        ArrayList<Ticket> tList = ticketService.getAll();
        return getPageTicket(model, "1", tList);
    }

    @GetMapping("/getAllTicket/{page}")
    public String getPageTicket(Model model, @PathVariable(value = "page") String currentPage,
            @ModelAttribute("tList") ArrayList<Ticket> tList) {
        ArrayList<Ticket> ticketList = new ArrayList<>();
        int page = 10;
        int numPages = (int) Math.ceil((float) tList.size() / page);
        int[] numPage = new int[numPages];
        for (int i = 0; i < numPages; i++) {
            numPage[i] = i + 1;
        }

        for (int i = (Integer.parseInt(currentPage) - 1) * page; i < Integer.parseInt(currentPage)
                * page; i++) {
            if (tList.size() <= i)
                break;
            ticketList.add(tList.get(i));
        }
        model.addAttribute("ticketList", ticketList);
        model.addAttribute("numPage", numPage);
        model.addAttribute("currentPage", Integer.parseInt(currentPage));
        model.addAttribute("Previous", Integer.parseInt(currentPage) - 1);
        model.addAttribute("Next", Integer.parseInt(currentPage) + 1);
        return "Employee/ticket";
    }

    // blog
    private Set<Image> images = new HashSet<>();
    @GetMapping("/getAllBlog")
    public String getAllBlogs(Model model) {
        ArrayList<Image> imgList = imageService.getAll();
        for (Image image : imgList) {
            images.add(image);
        }
        return getPageBlog(model, "1");
    }

    @GetMapping("/getAllBlog/{page}")
    public String getPageBlog(Model model, @PathVariable(value = "page") String currentPage) {
        ArrayList<Image> imageListt = new ArrayList<>(images);
        System.out.println(imageListt.size() + "imgListt");
        int imagePerPage = 10;
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
        System.out.println(imageList.size() + "imgList");
        model.addAttribute("imageList", imageList);
        model.addAttribute("numPage", numPage);
        model.addAttribute("currentPage", Integer.parseInt(currentPage));
        model.addAttribute("Previous", Integer.parseInt(currentPage) - 1);
        model.addAttribute("Next", Integer.parseInt(currentPage) + 1);
        return "Employee/blog";
    }

    // feedback
    @GetMapping("/getAllFeedback")
    public String getAllFeedbacks(Model model) {
        ArrayList<FeedBack> feedList = feedBackService.getAll();
        model.addAttribute("feedList", feedList);
        return getPageFeedback(model, "1", feedList);
    }

    @GetMapping("/getAllFeedback/{page}")
    public String getPageFeedback(Model model, @PathVariable(value = "page") String currentPage,
            @ModelAttribute("feedList") ArrayList<FeedBack> feedList) {
        ArrayList<FeedBack> feedbackList = new ArrayList<>();
        feedList = feedBackService.getAll();
        int page = 20;
        int numPages = (int) Math.ceil((float) feedList.size() / page);
        int[] numPage = new int[numPages];
        for (int i = 0; i < numPages; i++) {
            numPage[i] = i + 1;
        }

        for (int i = (Integer.parseInt(currentPage) - 1) * page; i < Integer.parseInt(currentPage)
                * page; i++) {
            if (feedList.size() <= i)
                break;
            feedbackList.add(feedList.get(i));
        }
        model.addAttribute("feedbackList", feedbackList);
        model.addAttribute("numPage", numPage);
        model.addAttribute("currentPage", Integer.parseInt(currentPage));
        model.addAttribute("Previous", Integer.parseInt(currentPage) - 1);
        model.addAttribute("Next", Integer.parseInt(currentPage) + 1);
        return "Employee/feedback";
    }


    // comment
    @GetMapping("/getAllComment")
    public String getAllComments(Model model) {
        ArrayList<Comment> comList = commentService.getAll();
        model.addAttribute("comList", comList);
        return getPageComment(model, "1", comList);
    }

    @GetMapping("/getAllComment/{page}")
    public String getPageComment(Model model, @PathVariable(value = "page") String currentPage,
            @ModelAttribute("comList") ArrayList<Comment> comList) {
        ArrayList<Comment> commentList = new ArrayList<>();
        comList = commentService.getAll();
        int page = 20;
        int numPages = (int) Math.ceil((float) comList.size() / page);
        int[] numPage = new int[numPages];
        for (int i = 0; i < numPages; i++) {
            numPage[i] = i + 1;
        }

        for (int i = (Integer.parseInt(currentPage) - 1) * page; i < Integer.parseInt(currentPage)
                * page; i++) {
            if (comList.size() <= i)
                break;
            commentList.add(comList.get(i));
        }
        model.addAttribute("commentList", commentList);
        model.addAttribute("numPage", numPage);
        model.addAttribute("currentPage", Integer.parseInt(currentPage));
        model.addAttribute("Previous", Integer.parseInt(currentPage) - 1);
        model.addAttribute("Next", Integer.parseInt(currentPage) + 1);
        return "Employee/comment";
    }

    
    // roomType
    @GetMapping("/getAllRoomType")
    public String getAllRoomType(Model model) {
        ArrayList<RoomType> roomTypeList = roomTypeService.getAll();
        model.addAttribute("roomTypeList", roomTypeList);
        return "employee/roomType";
    }

    // room
    @GetMapping("/getAllRoom")
    public String getAllRoom(Model model) {
        ArrayList<Room> roomList = roomService.getAll();
        model.addAttribute("roomList", roomList);
        return getPageRoom(model, "1", roomList);
    }

    @GetMapping("/getAllRoom/{page}")
    public String getPageRoom(Model model, @PathVariable(value = "page") String currentPage,
            @ModelAttribute("roomList") ArrayList<Room> roomList) {
        roomList.clear();
        roomList = roomService.getAll();
        ArrayList<Room> rList = new ArrayList<>();
        int page = 10;
        int numPages = (int) Math.ceil((float) roomList.size() / page);
        int[] numPage = new int[numPages];
        for (int i = 0; i < numPages; i++) {
            numPage[i] = i + 1;
        }

        for (int i = (Integer.parseInt(currentPage) - 1) * page; i < Integer.parseInt(currentPage)
                * page; i++) {
            if (roomList.size() <= i)
                break;
            rList.add(roomList.get(i));
        }
        model.addAttribute("rList", rList);
        model.addAttribute("numPage", numPage);
        model.addAttribute("currentPage", Integer.parseInt(currentPage));
        model.addAttribute("Previous", Integer.parseInt(currentPage) - 1);
        model.addAttribute("Next", Integer.parseInt(currentPage) + 1);
        return "Employee/room";
    }

}
