package com.giap.controller;

import com.giap.entity.User;
import com.giap.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String listUsers(@RequestParam(required = false) String search, Model model) {
        List<User> users;
        if (search != null && !search.trim().isEmpty()) {
            users = userService.findAll().stream()
                .filter(u -> u.getUsername().toLowerCase().contains(search.toLowerCase()) ||
                           (u.getFullname() != null && u.getFullname().toLowerCase().contains(search.toLowerCase())) ||
                           (u.getEmail() != null && u.getEmail().toLowerCase().contains(search.toLowerCase())))
                .toList();
            model.addAttribute("search", search);
        } else {
            users = userService.findAll();
        }
        model.addAttribute("users", users);
        model.addAttribute("title", "Quản lý User");
        return "admin/users/list";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "Thêm User mới");
        return "admin/users/form";
    }
    
    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute User user, 
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Thêm User mới");
            return "admin/users/form";
        }
        try {
            // Kiểm tra username đã tồn tại chưa
            Optional<User> existingUser = userService.findByUsername(user.getUsername());
            if (existingUser.isPresent()) {
                model.addAttribute("error", "Username đã tồn tại!");
                model.addAttribute("title", "Thêm User mới");
                return "admin/users/form";
            }
            userService.save(user);
            redirectAttributes.addFlashAttribute("success", "Tạo user thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            model.addAttribute("title", "Sửa User");
            return "admin/users/form";
        }
        redirectAttributes.addFlashAttribute("error", "Không tìm thấy user!");
        return "redirect:/admin/users";
    }
    
    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id, 
                           @Valid @ModelAttribute User user,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Sửa User");
            return "admin/users/form";
        }
        try {
            // Kiểm tra username đã tồn tại chưa (trừ user hiện tại)
            Optional<User> existingUser = userService.findByUsername(user.getUsername());
            if (existingUser.isPresent() && existingUser.get().getId() != id) {
                model.addAttribute("error", "Username đã tồn tại!");
                model.addAttribute("title", "Sửa User");
                return "admin/users/form";
            }
            userService.update(id, user);
            redirectAttributes.addFlashAttribute("success", "Cập nhật user thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<User> user = userService.findById(id);
            if (user.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy user để xóa!");
                return "redirect:/admin/users";
            }
            userService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Xóa user thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }
}

