package com.giap.baitap08.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class WebController {

    @ModelAttribute("avatarUrl")
    public String getAvatarUrl() {
        return "/images/profile.jpg";
    }

    @ModelAttribute("userName")
    public String getUserName() {
        return "Nguyễn Hoàng Giáp";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/products")
    public String products() {
        return "products";
    }

    @GetMapping("/users")
    public String users() {
        return "users";
    }

    @GetMapping("/categories")
    public String categories() {
        return "categories";
    }
}

