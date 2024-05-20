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
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Repository.CustomerTypeRepository;
import com.KDLST.Manager.Model.Service.UserServiceImplement;
import java.util.ArrayList;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static User user;
    Random random = new Random();
    @Autowired
    UserServiceImplement userServiceImplement = new UserServiceImplement();
    CustomerTypeRepository customerTypeRepository = new CustomerTypeRepository();

    @GetMapping("/showLogin")
    public String showLogin(Model model, HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        User user = new User();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userCookie".equals(cookie.getName())) {
                    String userStr = cookie.getValue();
                    // Xử lý logic với username

                    user = userServiceImplement.login(userStr);
                    model.addAttribute("user", user);
                    return "redirect:/";
                }
            }
        }
        model.addAttribute("user", user);
        return "login";
    }

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
                session.setAttribute("userRole", user.getRole());
                return "redirect:";
            } else {
                return showLogin(model, request);
            }
        } else if (!Boolean.TRUE.equals(rememberme)) {
            if (flag) {
                user = userServiceImplement.login(user1.getEmail());
                return "redirect:";
            } else {
                return showLogin(model, request);
            }
        }
        return showLogin(model, request);
    }

    @GetMapping(value = { "/showRegister" })
    public String showRegister(Model model, String mess) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("mess", mess);
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(Model model, @ModelAttribute("user") User user1,
            @RequestParam(name = "passAgain") String pass, @RequestParam(name = "birth") String birth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate;
        try {
            utilDate = dateFormat.parse(birth);
            Date sqlDate = new Date(utilDate.getTime());
            user1.setDob(sqlDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user1.setRole("USER");
        user1.setCustomerType(customerTypeRepository.getById(1));
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

    @PostMapping("/toAdd")
    public String toAdd() {
        userServiceImplement.add(user);
        user = null;
        return "redirect:/user/showLogin";

    }

    @GetMapping("/changePass")
    public String changePass() {
        return "ChangePass";
    }

    @PostMapping("/toChangePass")
    public String toChangePass(@RequestParam(name = "email") String email, Model model) {
        int randomNumber = random.nextInt(90000) + 10000;
        model.addAttribute("code", randomNumber);
        userServiceImplement.sendMail(email, "Code change password for you",
                randomNumber + "");
        user = userServiceImplement.login(email);
        return "ToChangePass";
    }

    @PostMapping("/changePassword")
    public String changePass(@RequestParam(name = "password") String password) {
        User user1 = userServiceImplement.login(user.getEmail());
        user1.setPassword(password);
        userServiceImplement.update(user1);
        return "redirect:/user/showLogin";
    }
}