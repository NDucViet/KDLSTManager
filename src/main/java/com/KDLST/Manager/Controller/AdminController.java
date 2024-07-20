package com.KDLST.Manager.Controller;

import com.KDLST.Manager.Model.Entity.Bill.*;
import com.KDLST.Manager.Model.Entity.Blog.Blog;
import com.KDLST.Manager.Model.Entity.Blog.BlogType;
import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoomDetails;
import com.KDLST.Manager.Model.Service.BillService.BillDetailsService;
import com.KDLST.Manager.Model.Service.BillService.BillDetailsServiceImplement;
import com.KDLST.Manager.Model.Service.BillService.BillService;
import com.KDLST.Manager.Model.Service.BillService.BillServiceImplement;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomDetailsService;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomDetailsServiceImplement;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomService;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomServiceImplement;
import com.KDLST.Manager.Model.Service.ImageBlogService.ImageService;
import com.KDLST.Manager.Model.Service.ImageBlogService.ImageServiceImplement;
import com.KDLST.Manager.Model.Service.RateAFbService.CommentService;
import com.KDLST.Manager.Model.Service.RateAFbService.CommentServiceImplement;
import com.KDLST.Manager.Model.Service.RateAFbService.FeedBackService;
import com.KDLST.Manager.Model.Service.RateAFbService.FeedBackServiceImplement;
import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import com.KDLST.Manager.Model.Entity.ServiceProject.ServiceType;
import com.KDLST.Manager.Model.Service.BillService.*;
import com.KDLST.Manager.Model.Service.BlogService.BlogServiceImplement;
import com.KDLST.Manager.Model.Service.BlogService.BlogTypeServiceImplement;
import com.KDLST.Manager.Model.Service.BookingRoomService.*;
import com.KDLST.Manager.Model.Service.HotelService.*;
import com.KDLST.Manager.Model.Service.ImageBlogService.*;
import com.KDLST.Manager.Model.Service.RateAFbService.*;
import com.KDLST.Manager.Model.Service.ServiceProjectService.*;
import com.KDLST.Manager.Model.Entity.Ticket.Ticket;
import com.KDLST.Manager.Model.Entity.Ticket.TicketSold;
import com.KDLST.Manager.Model.Entity.Ticket.TicketType;
import com.KDLST.Manager.Model.Service.TicketService.*;
import com.KDLST.Manager.Model.Service.UploadFile.StorageService;
import com.KDLST.Manager.Model.Service.UserService.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Date;
import java.time.LocalDate;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoom;
import com.KDLST.Manager.Model.Entity.Hotel.*;
import com.KDLST.Manager.Model.Entity.ImageBlog.Image;
import com.KDLST.Manager.Model.Entity.RateAFb.*;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${liveUpload.path}")
    private String liveUploadPath;

    @Autowired
    private BlogServiceImplement blogServiceImplement = new BlogServiceImplement();
    private ImageServiceImplement imageServiceImplement = new ImageServiceImplement();
    private BlogTypeServiceImplement blogTypeServiceImplement = new BlogTypeServiceImplement();
    @Autowired
    private StorageService storageService;
    ArrayList<BillDetails> billDetails = new ArrayList<>();
    ArrayList<BookingRoomDetails> bookingRoomDetails = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Bill> bills = new ArrayList<>();
    ArrayList<BookingRoom> bookingRooms = new ArrayList<>();
    ArrayList<User> cList = new ArrayList<>();
    ArrayList<User> eList = new ArrayList<>();
    @Autowired
    ServiceService serviceService = new ServiceServiceImplement();
    ServiceTypeService serviceTypeService = new ServiceTypeServiceImplement();
    BillDetailsService billDetailsService = new BillDetailsServiceImplement();
    BookingRoomDetailsService bookingRoomDetailsService = new BookingRoomDetailsServiceImplement();
    TicketService ticketService = new TicketServiceImplement();
    TicketTypeService ticketTypeService = new TicketTypeServiceImplement();
    UserService userService = new UserServiceImplement();
    UserServiceImplement userServiceImplement = new UserServiceImplement();
    BookingRoomService bookingRoomService = new BookingRoomServiceImplement();
    BillService billService = new BillServiceImplement();
    RoomTypeService roomTypeService = new RoomTypeServiceImplement();
    RoomService roomService = new RoomServiceImplement();
    ImageService imageService = new ImageServiceImplement();
    FeedBackService feedBackService = new FeedBackServiceImplement();
    CommentService commentService = new CommentServiceImplement();
    TicketSoldService ticketSoldService = new TicketSoldImplement();
    @GetMapping("/")
    public String index(@RequestParam(value = "year", defaultValue = "") String year, Model model) throws JsonProcessingException {
        // Fetch distinct years for the dropdown
        ArrayList<String> years = billDetailsService.getYearRevenue();
    
        Map<String, Double> monthlyTotals = new LinkedHashMap<>();
        Map<String, Double> monthlyTotalsHotels = new LinkedHashMap<>();
        Map<String, Double> monthlyTotals1 = new LinkedHashMap<>();
        Map<String, Double> monthlyTotalsHotels1 = new LinkedHashMap<>();
        
        java.util.Date utilDate = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(utilDate);
        String currentYear = formattedDate.split("/")[2];
    
        // Default to current year if none provided
        if (year.isEmpty()) {
            year = currentYear;
        }
    
        monthlyTotals1 = billDetailsService.getMonthlyRevenue(year);
        monthlyTotalsHotels1 = bookingRoomDetailsService.getMonthlyRevenue(year);
    
        if (monthlyTotals.size() != 12 && monthlyTotalsHotels.size() != 12) {
            for (int i = 1; i < 13; i++) {
                String key = String.valueOf(i);
                if (!monthlyTotals1.containsKey(key)) {
                    monthlyTotals.put(key, 0.0);
                } else {
                    monthlyTotals.put(key, monthlyTotals1.get(key));
                }
                if (!monthlyTotalsHotels1.containsKey(key)) {
                    monthlyTotalsHotels.put(key, 0.0);
                } else {
                    monthlyTotalsHotels.put(key, monthlyTotalsHotels1.get(key));
                }
            }
        }
    
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMonthlyTotals = objectMapper.writeValueAsString(monthlyTotals);
        String jsonMonthlyTotals1 = objectMapper.writeValueAsString(monthlyTotalsHotels);
    
        bookingRooms = bookingRoomService.getAll();
        users = userService.getAll();
    
        int customer = 0;
        int employee = 0;
    
        for (User user : users) {
            if (user.getRole().equals("CUSTOMER")) {
                customer += 1;
            } else if (user.getRole().equals("EMPLOYEE")) {
                employee += 1;
            }
        }
    
        model.addAttribute("data2", jsonMonthlyTotals1);
        model.addAttribute("data1", jsonMonthlyTotals);
        model.addAttribute("service", serviceService.getAll().size());
        model.addAttribute("order", bills.size() + bookingRooms.size());
        model.addAttribute("customer", customer);
        model.addAttribute("employee", employee);
        model.addAttribute("selectedYear", year); // Add this attribute for year selection
        model.addAttribute("years", years); // Add this attribute to populate the dropdown
        return "Admin/index";
    }
    
    

    // customer
    @GetMapping("/getAllCustomer")
    public String getAllCustomer(Model model) {
        ArrayList<User> cuList = userService.getAllCustomer();
        return getPageCustomer(model, "1", cuList);
    }

    @GetMapping("/getAllCustomer/{page}")
    public String getPageCustomer(Model model, @PathVariable(value = "page") String currentPage,
            @ModelAttribute("cList") ArrayList<User> cuList) {
        cuList.clear();
        ArrayList<User> customerList = new ArrayList<>();
        cuList = userService.getAllCustomer();
        int customerPage = 10;
        int numPages = (int) Math.ceil((float) cuList.size() / customerPage);
        int[] numPage = new int[numPages];
        for (int i = 0; i < numPages; i++) {
            numPage[i] = i + 1;
        }
        for (int i = (Integer.parseInt(currentPage) - 1) * customerPage; i < Integer.parseInt(currentPage)
                * customerPage; i++) {
            if (cuList.size() <= i)
                break;
            customerList.add(cuList.get(i));
        }
        model.addAttribute("customerList", customerList);
        model.addAttribute("numPage", numPage);
        model.addAttribute("currentPage", Integer.parseInt(currentPage));
        model.addAttribute("Previous", Integer.parseInt(currentPage) - 1);
        model.addAttribute("Next", Integer.parseInt(currentPage) + 1);
        return "Admin/customer";
    }

    @PostMapping(value = "/banCustomer")
    public ResponseEntity<String> delete(@RequestParam("userId") int id) {
        User user = userService.getById(id);
        boolean status = userService.banCustomer(user);
        System.out.println(status);
        return ResponseEntity.ok().body("Xoá thành công");
    }

    // employee
    @GetMapping("/getAllEmployee")
    public String getAllEmployees(Model model) {
        ArrayList<User> emList = userService.getAllEmployee();
        return getPageEmployee(model, "1", emList);

    }

    @GetMapping("/getAllEmployee/{page}")
    public String getPageEmployee(Model model, @PathVariable(value = "page") String currentPage,
            @ModelAttribute("emList") ArrayList<User> emList) {
        ArrayList<User> employeeList = new ArrayList<>();
        emList.clear();
        emList = userService.getAllEmployee();
        int employeePage = 10;
        int numPages = (int) Math.ceil((float) emList.size() / employeePage);
        int[] numPage = new int[numPages];

        for (int i = 0; i < numPages; i++) {
            numPage[i] = i + 1;
        }

        for (int i = (Integer.parseInt(currentPage) - 1) * employeePage; i < Integer.parseInt(currentPage)
                * employeePage; i++) {
            if (emList.size() <= i)
                break;
            employeeList.add(emList.get(i));
        }
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("numPage", numPage);
        model.addAttribute("currentPage", Integer.parseInt(currentPage));
        model.addAttribute("Previous", Integer.parseInt(currentPage) - 1);
        model.addAttribute("Next", Integer.parseInt(currentPage) + 1);
        return "Admin/employee";
    }

    @PostMapping(value = "/banEmployee")
    public ResponseEntity<String> deleteEmployee(@RequestParam("userId") int id) {
        User user = userService.getById(id);
        boolean status = userService.banCustomer(user);
        System.out.println(status);
        return ResponseEntity.ok().body("Xoá thành công");
    }

    @GetMapping("/addEmployee")
    public String showAddEmployeeForm(Model model, @RequestParam(name = "mess", required = false) String mess) {
        model.addAttribute("user", new User());
        model.addAttribute("mess", mess);
        return "Admin/employeeAdd";
    }

    @PostMapping("/addEmployee/action")
    public String addEmployee(@ModelAttribute("user") User user,
            @RequestParam(name = "birth") String birth,
            RedirectAttributes redirectAttributes,
            Model model) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date utilDate = dateFormat.parse(birth);
            Date sqlDate = new Date(utilDate.getTime());
            user.setDob(sqlDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        user.setCustomerType(null);
        user.setPassword("12345678");
        user.setStatus(true);
        user.setAvatar("UserAvatarDefault.jpg");
        user.setRole("EMPLOYEE");

        ArrayList<String> invalidAttributes = userServiceImplement.getInvalidAttributes(user);
        if (invalidAttributes.isEmpty()) {
            if (userService.add(user)) {
                redirectAttributes.addFlashAttribute("successMessage", "Đã thêm thành công!");
            }
        } else {
            ArrayList<String> errr = new ArrayList<>();
            errr = userServiceImplement.getInvalidAttributes(user);
            String err = "";
            for (String loi : errr) {
                err = err + " " + loi;
            }
            System.out.println(err);
            return showAddEmployeeForm(model, err);
        }
        return "redirect:/admin/getAllEmployee";
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
        serList.clear();
        ArrayList<Services> serviceList = new ArrayList<>();
        serList = serviceService.getAll();
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
        return "Admin/service";
    }

    @GetMapping("/addService")
    public String showAddServiceForm(Model model, Services service) {
        model.addAttribute("service", service);
        model.addAttribute("serviceType", serviceTypeService.getAll()); // Assuming
        return "Admin/serviceAdd";
    }

    @PostMapping("/addService/action")
    public String addService(@ModelAttribute Services service,
            @RequestParam(value = "id") int serviceID,
            @RequestParam("image1") MultipartFile imageFile, Model model,
            RedirectAttributes redirectAttributes) throws ServletException, IOException {
        System.out.println("ok");
        ServiceType serviceType = serviceTypeService.getById((serviceID));
        service.setServiceTypeID(serviceType);
        // set time
        LocalDate today = LocalDate.now();
        Date sqlDate = Date.valueOf(today);
        service.setDateTimeEdit(sqlDate);

        // image
        Path uploadDirPath = Paths.get(uploadPath);
        Path liveUploadDirPath = Paths.get(liveUploadPath);

        if (!Files.exists(uploadDirPath)) {
            Files.createDirectories(uploadDirPath);
        }

        if (!Files.exists(liveUploadDirPath)) {
            Files.createDirectories(liveUploadDirPath);
        }
        if (!imageFile.isEmpty()) {
            Path fileNameAndPath = Paths.get(uploadPath, imageFile.getOriginalFilename());
            Path liveFileNameAndPath = Paths.get(liveUploadPath, imageFile.getOriginalFilename());
            try {
                Files.write(fileNameAndPath, imageFile.getBytes());
                Files.write(liveFileNameAndPath, imageFile.getBytes());
                service.setImage(imageFile.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                return "Admin/serviceAdd";
            }
        }
        if (serviceService.add(service)) {
            redirectAttributes.addFlashAttribute("successMessage", "Đã thêm thành công!");
        }
        return "redirect:/admin/getAllService"; // Redirect to the tickets list page
    }

    @GetMapping("/updateService/{serviceID}")
    public String showUpdateServiceForm(@PathVariable("serviceID") int id, Model model) {
        Services service = serviceService.getById(id);
        model.addAttribute("service", service);
        model.addAttribute("serviceType", serviceTypeService.getAll()); // Assuming TicketType is an enum
        return "Admin/serviceView";
    }

    @PostMapping("/updateService/action")
    public String updateService(@ModelAttribute Services service,
            @RequestParam(value = "serviceID") int serviceID,
            @RequestParam("image1") MultipartFile imageFile,
            Model model,
            RedirectAttributes redirectAttributes) {
        Services oldService = serviceService.getById((serviceID));
        service.setServiceTypeID(oldService.getServiceTypeID());
        service.setServiceID((serviceID));
        LocalDate today = LocalDate.now();
        Date sqlDate = Date.valueOf(today);
        service.setDateTimeEdit(sqlDate);
        if (!imageFile.isEmpty()) {

            if (oldService.getImage() != null && !oldService.getImage().isEmpty()) {
                Path oldFilePath = Paths.get(uploadPath, oldService.getImage());
                Path oldLiveFilePath = Paths.get(liveUploadPath, oldService.getImage());

                try {
                    Files.deleteIfExists(oldFilePath);
                    Files.deleteIfExists(oldLiveFilePath);
                } catch (IOException e) {
                    // Xử lý ngoại lệ nếu tệp không thể xóa
                    System.err.println("Could not delete file: " + e.getMessage());
                    return "Admin/serviceAdd";
                }
            }
            Path fileNameAndPath = Paths.get(uploadPath, imageFile.getOriginalFilename());
            Path liveFileNameAndPath = Paths.get(liveUploadPath, imageFile.getOriginalFilename());
            try {
                Files.write(fileNameAndPath, imageFile.getBytes());
                Files.write(liveFileNameAndPath, imageFile.getBytes());
                service.setImage(imageFile.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                return "Admin/serviceAdd";
            }
        } else {
            service.setImage(oldService.getImage());
        }
        if (serviceService.update(service)) {
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
        }
        return "redirect:/admin/getAllService";
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
        tList.clear();
        ArrayList<Ticket> ticketList = new ArrayList<>();
        tList = ticketService.getAll();
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
        return "Admin/ticket";
    }

    @GetMapping("/addTicket")
    public String showAddTicketForm(Model model, Ticket ticket) {
        model.addAttribute("ticket", ticket);
        return "Admin/ticketAdd";
    }

    @PostMapping("/addTicket/action")
    public String addTicket(@ModelAttribute Ticket ticket,
            @RequestParam(value = "id") String ticketTypeID,
            @RequestParam("image1") MultipartFile imageFile, Model model, RedirectAttributes redirectAttributes)
            throws ServletException, IOException {
        TicketType ticketType = ticketTypeService.getByID(Integer.parseInt(ticketTypeID));
        ticket.setTicketTypeID(ticketType);
        Path uploadDirPath = Paths.get(uploadPath);
        Path liveUploadDirPath = Paths.get(liveUploadPath);

        if (!Files.exists(uploadDirPath)) {
            Files.createDirectories(uploadDirPath);
        }

        if (!Files.exists(liveUploadDirPath)) {
            Files.createDirectories(liveUploadDirPath);
        }
        if (!imageFile.isEmpty()) {
            Path fileNameAndPath = Paths.get(uploadPath, imageFile.getOriginalFilename());
            Path liveFileNameAndPath = Paths.get(liveUploadPath, imageFile.getOriginalFilename());
            try {
                Files.write(fileNameAndPath, imageFile.getBytes());
                Files.write(liveFileNameAndPath, imageFile.getBytes());
                ticket.setImage(imageFile.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                return "Admin/ticketAdd";
            }
        }
        ticket.setStatus(true);
        if (ticketService.add(ticket)) {
            redirectAttributes.addFlashAttribute("successMessage", "Đã thêm thành công!");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Thêm thất bại!");
        }

        return "redirect:/admin/getAllTicket"; // Redirect to the tickets list page
    }

    @GetMapping("/updateTicket/{ticketID}")
    public String showUpdateTicketForm(@PathVariable("ticketID") int id, Model model) {
        Ticket ticket = ticketService.getByID(id);
        model.addAttribute("ticket", ticket);
        model.addAttribute("ticketTypes", ticketTypeService.getAll()); // Assuming TicketType is an enum
        return "Admin/ticketView";
    }

    @PostMapping("/updateTicket/action")
    public String updateTicket(@ModelAttribute Ticket ticket,
            @RequestParam(value = "ticketID") String ticketID,
            @RequestParam("image1") MultipartFile imageFile,
            RedirectAttributes redirectAttributes,
            Model model) throws ServletException, IOException {
        Ticket oldTicket = ticketService.getByID(Integer.parseInt(ticketID));
        ticket.setTicketTypeID(oldTicket.getTicketTypeID());
        ticket.setTicketID(Integer.parseInt(ticketID));

        if (!imageFile.isEmpty()) {

            if (oldTicket.getImage() != null && !oldTicket.getImage().isEmpty()) {
                Path oldFilePath = Paths.get(uploadPath, oldTicket.getImage());
                Path oldLiveFilePath = Paths.get(liveUploadPath, oldTicket.getImage());

                try {
                    Files.deleteIfExists(oldFilePath);
                    Files.deleteIfExists(oldLiveFilePath);
                } catch (IOException e) {
                    // Xử lý ngoại lệ nếu tệp không thể xóa
                    System.err.println("Could not delete file: " + e.getMessage());
                    return "Admin/ticketView";
                }
            }
            Path fileNameAndPath = Paths.get(uploadPath, imageFile.getOriginalFilename());
            Path liveFileNameAndPath = Paths.get(liveUploadPath, imageFile.getOriginalFilename());
            try {
                Files.write(fileNameAndPath, imageFile.getBytes());
                Files.write(liveFileNameAndPath, imageFile.getBytes());
                ticket.setImage(imageFile.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                return "Admin/ticketView";
            }
        } else {
            ticket.setImage(oldTicket.getImage());
        }
        if (ticketService.update(ticket)) {
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
        }
        return "redirect:/admin/getAllTicket";
    }

    // blog
    ArrayList<Blog> bList = new ArrayList<>();

    @GetMapping("/getAllBlog")
    public String getAllBlogs(Model model) {
        bList = blogServiceImplement.getAll();
        return getPageBlog(model, "1");
    }

    @GetMapping("/getAllBlog/{page}")
    public String getPageBlog(Model model, @PathVariable(value = "page") String currentPage) {
        ArrayList<Blog> blogList = new ArrayList<>();
        bList = blogServiceImplement.getAll();
        int imagePerPage = 10;
        int numPages = (int) Math.ceil((float) bList.size() / imagePerPage);
        int[] numPage = new int[numPages];
        for (int i = 0; i < numPages; i++) {
            numPage[i] = i + 1;
        }
        for (int i = (Integer.parseInt(currentPage) - 1) * imagePerPage; i < Integer.parseInt(currentPage)
                * imagePerPage; i++) {
            if (bList.size() <= i)
                break;
            blogList.add(bList.get(i));
        }
        model.addAttribute("blogList", blogList);
        model.addAttribute("numPage", numPage);
        model.addAttribute("currentPage", Integer.parseInt(currentPage));
        model.addAttribute("Previous", Integer.parseInt(currentPage) - 1);
        model.addAttribute("Next", Integer.parseInt(currentPage) + 1);
        return "Admin/blog";
    }

    @GetMapping("/addBlog")
    public String showBlogAdd(Model model, Blog blog, Image image1, Image image2) {
        model.addAttribute("blog", blog);
        model.addAttribute("image1", image1);
        model.addAttribute("image2", image2);
        return "Admin/blogAdd";
    }

    @PostMapping("/addBlog/action")
    public String addBlog(Model model,
            @ModelAttribute("blog") Blog blog1,
            @ModelAttribute("image1") Image image1,
            @ModelAttribute("image2") Image image2,
            @RequestParam("imageUrl1") MultipartFile imageUrl1,
            @RequestParam("imageUrl2") MultipartFile imageUrl2,
            @RequestParam(value = "blogTypeID") int blogTypeID,
            @RequestParam(value = "imageDescript1") String imageDescript1,
            @RequestParam(value = "imageDescript2") String imageDescript2,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
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
        Path uploadDirPath = Paths.get(uploadPath);
        Path liveUploadDirPath = Paths.get(liveUploadPath);

        if (!Files.exists(uploadDirPath)) {
            Files.createDirectories(uploadDirPath);
        }

        if (!Files.exists(liveUploadDirPath)) {
            Files.createDirectories(liveUploadDirPath);
        }
        if (!imageUrl1.isEmpty()) {
            Path fileNameAndPath = Paths.get(uploadPath, imageUrl1.getOriginalFilename());
            Path liveFileNameAndPath = Paths.get(liveUploadPath, imageUrl1.getOriginalFilename());
            try {
                Files.write(fileNameAndPath, imageUrl1.getBytes());
                Files.write(liveFileNameAndPath, imageUrl1.getBytes());
                image1.setImageUrl(imageUrl1.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                return "Admin/blogAdd";
            }
        }
        image1.setImageDescript(imageDescript1);
        image1.setBlog(blog1);
        imageServiceImplement.add(image1);

        // image2
        if (!imageUrl2.isEmpty()) {
            Path fileNameAndPath = Paths.get(uploadPath, imageUrl2.getOriginalFilename());
            Path liveFileNameAndPath = Paths.get(liveUploadPath, imageUrl2.getOriginalFilename());
            try {

                Files.write(fileNameAndPath, imageUrl2.getBytes());
                Files.write(liveFileNameAndPath, imageUrl2.getBytes());
                image2.setImageUrl(imageUrl2.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                return "Admin/blogAdd";
            }
        }
        image2.setImageDescript(imageDescript2);
        image2.setBlog(blog1);
        if (imageServiceImplement.add(image2)) {
            redirectAttributes.addFlashAttribute("successMessage", "Đã thêm thành công!");
        }
        return "redirect:/admin/getAllBlog";
    }

    @GetMapping("/updateBlog/{blogID}")
    public String showUpdateBlogForm(Model model,
            @PathVariable("blogID") int id) {
        Blog blog = blogServiceImplement.getById(id);
        ArrayList<Image> imgList = imageServiceImplement.getImagesByBlogID(id);
        Image image1 = imgList.get(0);
        Image image2 = imgList.get(1);
        model.addAttribute("blog", blog);
        model.addAttribute("image1", image1);
        model.addAttribute("image2", image2);
        model.addAttribute("imageDescript1", image1.getImageDescript());
        model.addAttribute("imageDescript2", image2.getImageDescript());
        return "Admin/blogView";
    }

    @PostMapping("updateBlog/action")
    public String updateBlog(Model model,
            @ModelAttribute("blog") Blog blog1,
            @ModelAttribute("image1") Image image1,
            @ModelAttribute("image2") Image image2,
            @RequestParam("imageUrl1") MultipartFile imageUrl1,
            @RequestParam("imageUrl2") MultipartFile imageUrl2,
            @RequestParam(value = "blogID") int blogID,
            @RequestParam(value = "imageDescript1") String imageDescript1,
            @RequestParam(value = "imageDescript2") String imageDescript2,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) throws ServletException, IOException {
        System.out.println(imageDescript1);
        System.out.println(imageDescript2);
        Blog oldBlog = blogServiceImplement.getById(blogID);
        ArrayList<Image> imgList = imageServiceImplement.getImagesByBlogID(blogID);
        Image oldImage1 = imgList.get(0);
        Image oldImage2 = imgList.get(1);
        blog1.setUser(oldBlog.getUser());
        blog1.setBlogType(oldBlog.getBlogType());

        // set time
        LocalDate today = LocalDate.now();
        Date sqlDate = Date.valueOf(today);
        blog1.setDateTimeEdit(sqlDate);
        blog1.setStatus(true);
        blog1.setBlogType(oldBlog.getBlogType());
        // add blog1
        blogServiceImplement.update(blog1);

        // image1
        Path uploadDirPath = Paths.get(uploadPath);
        Path liveUploadDirPath = Paths.get(liveUploadPath);

        if (!Files.exists(uploadDirPath)) {
            Files.createDirectories(uploadDirPath);
        }

        if (!Files.exists(liveUploadDirPath)) {
            Files.createDirectories(liveUploadDirPath);
        }
        if (!imageUrl1.isEmpty()) {

            if (oldImage1.getImageUrl() != null && !oldImage1.getImageUrl().isEmpty()) {
                Path oldFilePath = Paths.get(uploadPath, oldImage1.getImageUrl());
                Path oldLiveFilePath = Paths.get(liveUploadPath, oldImage1.getImageUrl());

                try {
                    Files.deleteIfExists(oldFilePath);
                    Files.deleteIfExists(oldLiveFilePath);
                } catch (IOException e) {
                    // Xử lý ngoại lệ nếu tệp không thể xóa
                    System.err.println("Could not delete file: " + e.getMessage());
                    return "Admin/blogView";
                }
            }
            Path fileNameAndPath = Paths.get(uploadPath, imageUrl1.getOriginalFilename());
            Path liveFileNameAndPath = Paths.get(liveUploadPath, imageUrl1.getOriginalFilename());
            try {
                Files.write(fileNameAndPath, imageUrl1.getBytes());
                Files.write(liveFileNameAndPath, imageUrl1.getBytes());
                image1.setImageUrl(imageUrl1.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                return "Admin/blogView";
            }
        } else {
            image1.setImageUrl(oldImage1.getImageUrl());
        }
        image1.setImageID(oldImage1.getImageID());
        image1.setImageDescript(imageDescript1);
        image1.setBlog(blog1);
        imageServiceImplement.update(image1);

        // image2
        if (!imageUrl2.isEmpty()) {

            if (oldImage1.getImageUrl() != null && !oldImage2.getImageUrl().isEmpty()) {
                Path oldFilePath = Paths.get(uploadPath, oldImage2.getImageUrl());
                Path oldLiveFilePath = Paths.get(liveUploadPath, oldImage2.getImageUrl());

                try {
                    Files.deleteIfExists(oldFilePath);
                    Files.deleteIfExists(oldLiveFilePath);
                } catch (IOException e) {
                    // Xử lý ngoại lệ nếu tệp không thể xóa
                    System.err.println("Could not delete file: " + e.getMessage());
                    return "Admin/blogView";
                }
            }
            Path fileNameAndPath = Paths.get(uploadPath, imageUrl2.getOriginalFilename());
            Path liveFileNameAndPath = Paths.get(liveUploadPath, imageUrl2.getOriginalFilename());
            try {
                Files.write(fileNameAndPath, imageUrl2.getBytes());
                Files.write(liveFileNameAndPath, imageUrl2.getBytes());
                image2.setImageUrl(imageUrl2.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                return "Admin/blogView";
            }
        } else {
            image2.setImageUrl(oldImage2.getImageUrl());
        }
        image2.setImageID(oldImage2.getImageID());
        image2.setImageDescript(imageDescript2);
        image2.setBlog(blog1);
        if (imageServiceImplement.update(image2)) {
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
        }
        return "redirect:/admin/getAllBlog";
    }

    @PostMapping(value = "/hiddenBlog")
    public ResponseEntity<String> hiddenBlog(@RequestParam("id") int id) {
        boolean status = blogServiceImplement.hiddenBlog(id);
        return ResponseEntity.ok().body("Xoá thành công");
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
        return "Admin/feedback";
    }

    @PostMapping(value = "/deleteFeedback")
    public ResponseEntity<String> deleteFeedback(@RequestParam("id") int id) {
        boolean status = feedBackService.delete(id);
        return ResponseEntity.ok().body("Xoá thành công");
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
        return "Admin/comment";
    }

    @PostMapping(value = "/deleteComment")
    public ResponseEntity<String> deleteComment(@RequestParam("id") int id) {
        boolean status = commentService.delete(id);
        System.out.println(status);
        return ResponseEntity.ok().body("Xoá thành công");
    }

    // roomType
    @GetMapping("/getAllRoomType")
    public String getAllRoomType(Model model) {
        ArrayList<RoomType> roomTypeList = roomTypeService.getAll();
        model.addAttribute("roomTypeList", roomTypeList);
        return "Admin/roomType";
    }

    @GetMapping("/updateRoomType/{roomTypeID}")
    public String showUpdateRoomForm(@PathVariable("roomTypeID") int id, Model model) {
        RoomType roomType = roomTypeService.getById(id);
        model.addAttribute("roomType", roomType);
        return "Admin/roomTypeView";
    }

    @PostMapping("/updateRoomType/action")
    public String updateRoom(@ModelAttribute RoomType roomType,
            @RequestParam(value = "roomTypeID") String roomTypeID,
            @RequestParam("image1") MultipartFile imageFile,
            RedirectAttributes redirectAttributes,
            Model model) {
        RoomType oldRoomType = roomTypeService.getById(Integer.parseInt(roomTypeID));
        roomType.setRoomTypeID(Integer.parseInt(roomTypeID));
        roomType.setQuantity(oldRoomType.getQuantity());
        if (!imageFile.isEmpty()) {

            if (oldRoomType.getImages() != null && !oldRoomType.getImages().isEmpty()) {
                Path oldFilePath = Paths.get(uploadPath, oldRoomType.getImages());
                Path oldLiveFilePath = Paths.get(liveUploadPath, oldRoomType.getImages());

                try {
                    Files.deleteIfExists(oldFilePath);
                    Files.deleteIfExists(oldLiveFilePath);
                } catch (IOException e) {
                    // Xử lý ngoại lệ nếu tệp không thể xóa
                    System.err.println("Could not delete file: " + e.getMessage());
                    return "Admin/roomTypeView";
                }
            }
            Path fileNameAndPath = Paths.get(uploadPath, imageFile.getOriginalFilename());
            Path liveFileNameAndPath = Paths.get(liveUploadPath, imageFile.getOriginalFilename());
            try {
                Files.write(fileNameAndPath, imageFile.getBytes());
                Files.write(liveFileNameAndPath, imageFile.getBytes());
                roomType.setImages(imageFile.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                return "Admin/roomTypeView";
            }
        } else {
            roomType.setImages(oldRoomType.getImages());
        }
        if (roomTypeService.update(roomType)) {
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
        }
        return "redirect:/admin/getAllRoomType";
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
        return "Admin/room";
    }

    @GetMapping("/getRevenue")
    public String getRevenue(Model model) throws JsonProcessingException {
        ArrayList<BookingRoomDetails> b = bookingRoomDetailsService.getAll();
        ArrayList<BillDetails> billDetails = billDetailsService.getAll();
        Map<String, Double> totalsMap = new LinkedHashMap<>();
        Map<String, Double> pieMap = new LinkedHashMap<>();
        Map<String, Double> hotelTotals = bookingRoomDetailsService.getYearsRevenue();
        Map<String, Double> serviceTotals = billDetailsService.getYearsRevenue();
        Map<String, Double> roomTypeMap = new LinkedHashMap<>();
        Map<String, Double> ticketMap = new LinkedHashMap<>();
        ArrayList<Ticket> tickets = ticketService.getAll();
        for (Ticket ticket : tickets) {
            ticketMap.put(ticket.getTitle(), 0.0);
        }
        for (BillDetails bDetails : billDetails) {
            String key = bDetails.getTicketID().getTitle();
            double value = ticketMap.get(key) + bDetails.getTotal();
            ticketMap.put(key, value);
        }
        roomTypeMap.put("Luxurious Room", 0.0);
        roomTypeMap.put("Family Room", 0.0);
        roomTypeMap.put("Couple Room", 0.0);
        roomTypeMap.put("Normal Room", 0.0);
        roomTypeMap.put("President Room", 0.0);
        for (BookingRoomDetails bookingRoomDetails : b) {
            if (roomTypeMap.containsKey(bookingRoomDetails.getRoom().getRoomType().getRoomTypeName())) {
                String key = bookingRoomDetails.getRoom().getRoomType().getRoomTypeName();
                Double value = bookingRoomDetails.getRoom().getRoomType().getPrice() + roomTypeMap.get(key);
                roomTypeMap.put(key, value);
            }
        }
        double pieHotel = 0.0;
        double pieService = 0.0;
        System.out.println(hotelTotals.toString());
        System.out.println(serviceTotals.toString());
        for (Map.Entry<String, Double> entry : hotelTotals.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            totalsMap.put(key, value);
            pieHotel += value;
        }
        for (Map.Entry<String, Double> entry : serviceTotals.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            if (totalsMap.containsKey(key)) {
                totalsMap.put(key, totalsMap.get(key) + value);
                pieService += value;
            } else {
                totalsMap.put(key, value);
                pieService += value;
            }
        }
        double totals = 0.0;
        for (Map.Entry<String, Double> entry : roomTypeMap.entrySet()) {
            totals += entry.getValue();
        }
        for (Map.Entry<String, Double> entry : roomTypeMap.entrySet()) {
            entry.setValue((entry.getValue() * 100) / totals);
        }
        double totals1 = 0.0;
        for (Map.Entry<String, Double> entry : ticketMap.entrySet()) {
            totals1 += entry.getValue();
        }
        for (Map.Entry<String, Double> entry : ticketMap.entrySet()) {
            entry.setValue((entry.getValue() * 100) / totals1);
        }
        pieMap.put("hotel", (pieHotel * 100) / (pieHotel + pieService));
        pieMap.put("service", 100 - ((pieHotel * 100) / (pieHotel + pieService)));
        model.addAttribute("ticket", ticketMap);
        model.addAttribute("roomType", roomTypeMap);
        model.addAttribute("totalsMap", totalsMap);
        model.addAttribute("pieMap", pieMap);
        return "Admin/chart";
    }

    // ticket static
    @GetMapping("/getTicketStatic")
    public String getTicketStatic(Model model) {
        ArrayList<TicketType> ticketTypes = ticketTypeService.getAll();
        Map<Ticket, Integer> ticketStatic = new LinkedHashMap<>();
        ArrayList<BillDetails> billDetails = billDetailsService.getAll();
        for (BillDetails bDetails : billDetails) {
            Ticket ticket = bDetails.getTicketID();
            int quantity = bDetails.getQuantity();
            if (ticketStatic.containsKey(ticket)) {
                quantity += ticketStatic.get(ticket);
            }
            ticketStatic.put(ticket, quantity);
        }
        model.addAttribute("ticketTypes", ticketTypes);
        model.addAttribute("ticketStatic", ticketStatic);
        return "Admin/ticketStatic";
    }

     @GetMapping("/getAllTicketSold")
    public String getAllTicketSold(Model model) {
        ArrayList<TicketSold> ticketSoldList = ticketSoldService.getAllTicketSold();
        model.addAttribute("ticketSoldList", ticketSoldList);
        return "Admin/checkTicket";
    }

    @PostMapping(value = "/checkTicket")
    public ResponseEntity<String> checkTicket(@RequestParam("id") String id) {
        TicketSold ticketSold = ticketSoldService.getByID(id);
        boolean status = ticketSoldService.update(ticketSold);
        System.out.println(status);
        return ResponseEntity.ok().body("Hủy trạng thái vé thành công");
    }

}
