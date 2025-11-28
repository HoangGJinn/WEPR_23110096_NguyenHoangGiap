package com.giap.controller;

import com.giap.entity.Category;
import com.giap.entity.User;
import com.giap.service.CategoryService;
import com.giap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "admin/categories/list";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/categories/form";
    }
    
    @PostMapping("/create")
    public String createCategory(@ModelAttribute Category category, 
                                 @RequestParam(required = false) Long userId,
                                 RedirectAttributes redirectAttributes) {
        try {
            if (userId != null) {
                Optional<User> user = userService.findById(userId);
                user.ifPresent(category::setUser);
            }
            categoryService.save(category);
            redirectAttributes.addFlashAttribute("success", "Tạo category thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/categories";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            List<User> users = userService.findAll();
            model.addAttribute("users", users);
            return "admin/categories/form";
        }
        redirectAttributes.addFlashAttribute("error", "Không tìm thấy category!");
        return "redirect:/admin/categories";
    }
    
    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Long id, 
                                @ModelAttribute Category category,
                                @RequestParam(required = false) Long userId,
                                RedirectAttributes redirectAttributes) {
        try {
            if (userId != null) {
                Optional<User> user = userService.findById(userId);
                user.ifPresent(category::setUser);
            }
            categoryService.update(id, category);
            redirectAttributes.addFlashAttribute("success", "Cập nhật category thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/categories";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Xóa category thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/categories";
    }
}

