package com.KDLST.Manager.Config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class RoleFilter implements Filter {

    private static final List<String> PUBLIC_PATH = new CopyOnWriteArrayList<>(Arrays.asList(
            "/user/showLogin", "/user/login", "/user/showRegister", "/user/register",
            "/user/toAdd", "/user/changePass",
            "/user/toChangePass", "/user/changePassword", "/ticket/getAll", "/hotel",
            "/service/getAll",
            "/service/getByID", "/service/searchService", "/service/serviceSuggestion",
            "/about", "/contact", "/hotel/about", "/hotel/contactUs", "/hotel/getDate",
            "/hotel/getRoom",
            "/hotel/getRoomType", "/hotel/getRoomByRoomType", "/hotel/getAllRoom",
            "/hotel/searchRoomType",
            "/hotel/roomTypeSuggestion", "/blog/getAll", "/blog/getByID",
            "/blog/showBlogDetail", "/css", "/images", "/js", "/user/403"));

    private static final List<String> USER_PATH = new CopyOnWriteArrayList<>(Arrays.asList(
            "/user/logout", "/user/profile", "/user/showEdit", "/user/edit",
            "/hotel/history", "/hotel/checkOut",
            "/hotel/vnpay-payment-return", "/cart/", "/cart/allItem", "/cart/delete",
            "/cart/update", "/cart/add",
            "/cart/checkOut", "/cart/vnpay-payment-return", "/cart/history",
            "/service/addFeedback",
            "/service/deleteFeedback", "/blog/submitComment", "/hotel/", "/css",
            "/images", "/js", "/user/403","/ticket/rating"));

    private static final List<String> EMPLOY_PATH = new CopyOnWriteArrayList<>(Arrays.asList(
            "/employee/", "/employee/addBlog", "/employee/addBlog/action", "/css",
            "/images", "/js", "/user/403"));

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getServletPath();
        System.out.println(path);

        if (isStaticResource(path) || isPublicPath(path) || path.equals("/")) {
            chain.doFilter(request, response);
            return;
        }

        String userRole = (String) httpRequest.getSession().getAttribute("userRole");
        System.out.println(userRole);
        if (userRole != null) {
            if (userRole.equals("ADMIN")) {
                chain.doFilter(request, response);
                return;
            } else if (userRole.equals("EMPLOYEE")) {
                if (isEmployPath(path)) {
                    chain.doFilter(request, response);
                    return;
                } else {
                    httpResponse.sendRedirect("/user/403");
                    return;
                }
            } else if (userRole.equals("CUSTOMER")) {
                if (isUserPath(path)) {
                    chain.doFilter(request, response);
                    return;
                } else {
                    httpResponse.sendRedirect("/user/403");
                    return;
                }
            } else {
                httpResponse.sendRedirect("/user/403");
                return;
            }
        } else if (isStaticResource(path) || isPublicPath(path) || path.equals("/")) {
            chain.doFilter(request, response);
            return;
        } else {
            httpResponse.sendRedirect("/user/showLogin");
            return;
        }
    }

    private boolean isPublicPath(String path) {
        for (String string : PUBLIC_PATH) {
            if (path.contains(string)) {
                return true;
            }
        }
        return false;
    }

    private boolean isUserPath(String path) {
        return USER_PATH.stream().anyMatch(path::equals);
    }

    private boolean isEmployPath(String path) {
        return EMPLOY_PATH.stream().anyMatch(path::equals);
    }

    private boolean isStaticResource(String path) {
        if (path.contains("/static/") || path.contains("/css/") ||
                path.contains("/images/") || path.contains("/js/")
                || path.contains("/fonts/")) {
            return true;
        }
        return false;
    }
}
