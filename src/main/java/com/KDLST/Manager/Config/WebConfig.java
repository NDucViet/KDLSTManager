package com.KDLST.Manager.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/hotel/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/user/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/blog/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/service/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/service/getAll/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/service/getByID/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/ticket/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/ticket/getAll/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/ticket/getByID/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/blog/getByID/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/blog/getAll/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/blog/showBlogDetail/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/employee/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/user/login/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/cart/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/admin/getAllCustomer/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/admin/getAllEmployee/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/admin/getAllService/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/admin/getAllBlog/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/admin/getAllComment/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/admin/getAllFeedback/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/admin/getAllRoom/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/admin/getAllTicket/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/employee/getAllCustomer/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/employee/getAllService/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/employee/getAllBlog/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/employee/getAllComment/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/employee/getAllFeedback/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/employee/getAllRoom/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/employee/getAllTicket/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/admin/updateTicket/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/admin/updateService/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/admin/updateRoomType/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/admin/updateBlog/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/employee/updateBlog/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/admin/addEmployee/**").addResourceLocations("classpath:/static/");
    }
}
