package com.KDLST.Manager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Service.UserServiceImplement;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserServiceImplement userServiceImplement = new UserServiceImplement();

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
                return "redirect:/";
            } else {
                return showLogin(model, request);
            }
        } else if (!Boolean.TRUE.equals(rememberme)) {
            if (flag) {
                user = userServiceImplement.login(user1.getEmail());
                return "redirect:/";
            } else {
                return showLogin(model, request);
            }
        }
        return showLogin(model, request);
    }
}
