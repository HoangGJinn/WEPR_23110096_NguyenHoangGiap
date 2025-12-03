package com.giap.controller;

import com.giap.service.CategoryService;
import com.giap.service.UserService;
import com.giap.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private VideoService videoService;
    
    @GetMapping
    public String dashboard(Model model) {
        // Thống kê tổng quan
        long totalUsers = userService.findAll().size();
        long totalCategories = categoryService.findAll().size();
        long totalVideos = videoService.findAll().size();
        long activeUsers = userService.findAll().stream()
            .filter(u -> u.getActive() != null && u.getActive())
            .count();
        long activeCategories = categoryService.findAll().stream()
            .filter(c -> c.getStatus() != null && c.getStatus())
            .count();
        long activeVideos = videoService.findAll().stream()
            .filter(v -> v.getActive() != null && v.getActive())
            .count();
        
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalCategories", totalCategories);
        model.addAttribute("totalVideos", totalVideos);
        model.addAttribute("activeUsers", activeUsers);
        model.addAttribute("activeCategories", activeCategories);
        model.addAttribute("activeVideos", activeVideos);
        model.addAttribute("title", "Dashboard");
        
        return "admin/dashboard";
    }
}

