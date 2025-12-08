package com.giap.controller;

import com.giap.entity.Category;
import com.giap.entity.User;
import com.giap.service.CategoryService;
import com.giap.service.UserService;
import com.giap.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories(
            @RequestParam(required = false) String search) {
        try {
            List<Category> categories;
            if (search != null && !search.trim().isEmpty()) {
                categories = categoryService.findByCategoryNameContaining(search);
            } else {
                categories = categoryService.findAll();
            }
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách category thành công", categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Lỗi khi lấy danh sách category: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable Long id) {
        try {
            Optional<Category> category = categoryService.findById(id);
            if (category.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Lấy category thành công", category.get()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Không tìm thấy category với id: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Lỗi khi lấy category: " + e.getMessage()));
        }
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<Category>> createCategory(
            @Valid @RequestBody Category category,
            @RequestParam(required = false) Long userId) {
        try {
            if (userId != null) {
                Optional<User> user = userService.findById(userId);
                if (user.isPresent()) {
                    category.setUser(user.get());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(ApiResponse.error("Không tìm thấy user với id: " + userId));
                }
            }
            Category savedCategory = categoryService.save(category);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Tạo category thành công", savedCategory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Lỗi khi tạo category: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody Category category) {
        try {
            Optional<Category> existingCategory = categoryService.findById(id);
            if (existingCategory.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Không tìm thấy category với id: " + id));
            }
            
            // Đảm bảo id từ path variable được sử dụng, ignore id từ body nếu có
            category.setId(id);
            
            // Nếu body không có user, giữ nguyên user hiện tại
            if (category.getUser() == null) {
                category.setUser(existingCategory.get().getUser());
            }
            
            Category updatedCategory = categoryService.update(id, category);
            return ResponseEntity.ok(ApiResponse.success("Cập nhật category thành công", updatedCategory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Lỗi khi cập nhật category: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteCategory(@PathVariable Long id) {
        try {
            Optional<Category> category = categoryService.findById(id);
            if (category.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Không tìm thấy category với id: " + id));
            }
            categoryService.deleteById(id);
            return ResponseEntity.ok(ApiResponse.success("Xóa category thành công", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Lỗi khi xóa category: " + e.getMessage()));
        }
    }
}
