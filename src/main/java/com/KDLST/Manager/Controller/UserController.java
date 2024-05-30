package com.KDLST.Manager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.sql.Date;

import com.KDLST.Manager.Model.Entity.CartItem.Cart;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Service.CartItemService.CartService;
import com.KDLST.Manager.Model.Service.UserService.CustomerTypeServiceImplement;
import com.KDLST.Manager.Model.Service.UserService.UserServiceImplement;
import java.util.ArrayList;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    // Tạo user toàn cục
    private static User user;

    // Tạo đối tượng random
    Random random = new Random();

    // Tiêm phụ thuộc
    @Autowired
    private UserServiceImplement userServiceImplement = new UserServiceImplement();
    private CustomerTypeServiceImplement customerTypeServiceImplement;
    private CartService cartService;

    // Hàm check cookie, trả về form đăng nhập
    @GetMapping("/showLogin")
    public String showLogin(Model model, HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        User user = new User();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userCookie".equals(cookie.getName())) {
                    String userStr = cookie.getValue();
                    user = userServiceImplement.login(userStr);
                    // Xử lý logic với username
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", user);
                    user = userServiceImplement.login(userStr);
                    model.addAttribute("user", user);
                    return new indexController().index();
                }
            }
        }
        model.addAttribute("user", user);
        return "login";
    }

    // Hàm check form và đăng nhập
    @PostMapping(value = "/login")
    public String toLogin(@ModelAttribute("user") User user1, Model model,
            @RequestParam(value = "agree", required = false) Boolean rememberme,
            HttpServletResponse response,
            HttpServletRequest request) {

        User user = new User();
        user1.setAddress(null);
        user1.setAvatar(null);
        user1.setDob(null);
        user1.setCustomerType(null);
        user1.setGender(0);
        user1.setNation(null);
        user1.setCardID(null);
        user1.setPhoneNumber(null);
        user1.setUsername(null);
        user1.setIdUser(0);
        user1.setStatus(null);
        user1.setRole(null);
        System.out.println(user1.toString());
        boolean flag = userServiceImplement.toLogin(user1);
        System.out.println(flag);
        if (Boolean.TRUE.equals(rememberme)) {
            System.out.println("cc");
            if (flag) {
                Cookie cookie = new Cookie("userCookie", user1.getEmail());
                user = userServiceImplement.login(user1.getEmail());
                cookie.setMaxAge(60 * 60);
                response.addCookie(cookie);
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                session.setAttribute("userRole", user.getRole());
                return new indexController().index();
            } else {
                return showLogin(model, request);
            }
        } else if (!Boolean.TRUE.equals(rememberme)) {
            if (flag) {
                user = userServiceImplement.login(user1.getEmail());
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                return new indexController().index();
            } else {
                return showLogin(model, request);
            }
        }
        return showLogin(model, request);
    }

    // Hàm trả về form đăng kí
    @GetMapping(value = { "/showRegister" })
    public String showRegister(Model model, String mess) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("mess", mess);
        return "register";
    }

    // Hàm check form đăng kí
    @PostMapping(value = "/register")
    public String register(Model model, @ModelAttribute("user") User user1,
            @RequestParam(name = "passAgain") String pass, @RequestParam(name = "birth") String birth,
            @RequestParam(name = "avatar") String avatarPathOrUrl) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate;
        try {
            utilDate = dateFormat.parse(birth);
            Date sqlDate = new Date(utilDate.getTime());
            user1.setDob(sqlDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user1.setAvatar(null);
        user1.setRole("USER");
        user1.setCustomerType(customerTypeServiceImplement.getById(6));
        user1.setIdUser(0);
        user1.setStatus(true);
        if (pass.equals(user1.getPassword())) {
            if (userServiceImplement.getInvalidAttributes(user1).isEmpty()
                    || userServiceImplement.getInvalidAttributes(user1) == null) {
                user = user1;

                int randomNumber = random.nextInt(90000) + 10000;
                model.addAttribute("code", randomNumber);
                userServiceImplement.sendMail(user1.getEmail(), "Code Login for you",
                        randomNumber + "");
                return "SubmitCode";
            } else {
                ArrayList<String> errr = new ArrayList<>();
                errr = userServiceImplement.getInvalidAttributes(user1);
                String err = "";
                for (String loi : errr) {
                    err = err + " " + loi;
                }
                System.out.println(err);
                return showRegister(model, err);
            }
        } else {
            String mess = "passAgain";
            return showRegister(model, mess);
        }
    }

    // Hàm add 1 User
    @GetMapping("/toAdd")
    public String toAdd() {
        userServiceImplement.add(user);
        user = userServiceImplement.login(user.getEmail());
        Cart cart = new Cart(0, user);
        cartService.add(cart);
        user = null;
        return "redirect:/user/showLogin";

    }

    // Hàm trả về form đổi mk
    @GetMapping("/changePass")
    public String changePass() {
        return "ChangePass";
    }

    // Hàm generate code đổi mk
    @PostMapping("/toChangePass")
    public String toChangePass(@RequestParam(name = "email") String email, Model model) {
        int randomNumber = random.nextInt(90000) + 10000;
        model.addAttribute("code", randomNumber);
        userServiceImplement.sendMail(email, "Code change password for you",
                randomNumber + "");
        user = userServiceImplement.login(email);
        return "ToChangePass";
    }

    // Hàm đổi mk
    @PostMapping("/changePassword")
    public String changePass(@RequestParam(name = "password") String password) {
        User user1 = userServiceImplement.login(user.getEmail());
        user1.setPassword(password);
        userServiceImplement.update(user1);
        return "redirect:/user/showLogin";
    }
}
