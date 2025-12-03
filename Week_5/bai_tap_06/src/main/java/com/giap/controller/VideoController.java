package com.giap.controller;

import com.giap.entity.Category;
import com.giap.entity.Video;
import com.giap.service.CategoryService;
import com.giap.service.VideoService;
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
@RequestMapping("/admin/videos")
public class VideoController {
    
    @Autowired
    private VideoService videoService;
    
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping
    public String listVideos(@RequestParam(required = false) String search, Model model) {
        List<Video> videos;
        if (search != null && !search.trim().isEmpty()) {
            videos = videoService.findAll().stream()
                .filter(v -> v.getTitle().toLowerCase().contains(search.toLowerCase()) ||
                           (v.getDescription() != null && v.getDescription().toLowerCase().contains(search.toLowerCase())))
                .toList();
            model.addAttribute("search", search);
        } else {
            videos = videoService.findAll();
        }
        model.addAttribute("videos", videos);
        model.addAttribute("title", "Quản lý Video");
        return "admin/videos/list";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("video", new Video());
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Thêm Video mới");
        return "admin/videos/form";
    }
    
    @PostMapping("/create")
    public String createVideo(@Valid @ModelAttribute Video video,
                             BindingResult bindingResult,
                             @RequestParam(required = false) Long categoryId,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            List<Category> categories = categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("title", "Thêm Video mới");
            return "admin/videos/form";
        }
        try {
            if (categoryId != null) {
                Optional<Category> category = categoryService.findById(categoryId);
                category.ifPresent(video::setCategory);
            }
            videoService.save(video);
            redirectAttributes.addFlashAttribute("success", "Tạo video thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/videos";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Video> video = videoService.findById(id);
        if (video.isPresent()) {
            model.addAttribute("video", video.get());
            List<Category> categories = categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("title", "Sửa Video");
            return "admin/videos/form";
        }
        redirectAttributes.addFlashAttribute("error", "Không tìm thấy video!");
        return "redirect:/admin/videos";
    }
    
    @PostMapping("/edit/{id}")
    public String updateVideo(@PathVariable Long id,
                             @Valid @ModelAttribute Video video,
                             BindingResult bindingResult,
                             @RequestParam(required = false) Long categoryId,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            List<Category> categories = categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("title", "Sửa Video");
            return "admin/videos/form";
        }
        try {
            if (categoryId != null) {
                Optional<Category> category = categoryService.findById(categoryId);
                category.ifPresent(video::setCategory);
            }
            videoService.update(id, video);
            redirectAttributes.addFlashAttribute("success", "Cập nhật video thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/videos";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteVideo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Video> video = videoService.findById(id);
            if (video.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy video để xóa!");
                return "redirect:/admin/videos";
            }
            videoService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Xóa video thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/videos";
    }
}

