package com.giap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminViewController {
    
    // Main admin page - Single Page Application
    @GetMapping
    public String admin() {
        return "admin/index";
    }
    
    // Redirect all admin routes to main page (SPA handles routing)
    @GetMapping("/users")
    public String users() {
        return "admin/index";
    }
    
    @GetMapping("/categories")
    public String categories() {
        return "admin/index";
    }
    
    @GetMapping("/videos")
    public String videos() {
        return "admin/index";
    }
}

