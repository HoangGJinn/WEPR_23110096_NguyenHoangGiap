package com.giap.controller;

import com.giap.service.CategoryService;
import com.giap.service.UserService;
import com.giap.service.VideoService;
import com.giap.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private VideoService videoService;
    
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<Map<String, Object>>> dashboard() {
        try {
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
            
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalUsers", totalUsers);
            stats.put("totalCategories", totalCategories);
            stats.put("totalVideos", totalVideos);
            stats.put("activeUsers", activeUsers);
            stats.put("activeCategories", activeCategories);
            stats.put("activeVideos", activeVideos);
            
            return ResponseEntity.ok(ApiResponse.success("Lấy thống kê thành công", stats));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Lỗi khi lấy thống kê: " + e.getMessage()));
        }
    }
}
