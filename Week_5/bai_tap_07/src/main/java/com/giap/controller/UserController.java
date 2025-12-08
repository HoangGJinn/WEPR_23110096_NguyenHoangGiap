package com.giap.controller;

import com.giap.entity.User;
import com.giap.service.UserService;
import com.giap.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers(
            @RequestParam(required = false) String search) {
        try {
            List<User> users;
            if (search != null && !search.trim().isEmpty()) {
                users = userService.findAll().stream()
                    .filter(u -> u.getUsername().toLowerCase().contains(search.toLowerCase()) ||
                               (u.getFullname() != null && u.getFullname().toLowerCase().contains(search.toLowerCase())) ||
                               (u.getEmail() != null && u.getEmail().toLowerCase().contains(search.toLowerCase())))
                    .toList();
            } else {
                users = userService.findAll();
            }
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách user thành công", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Lỗi khi lấy danh sách user: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        try {
            Optional<User> user = userService.findById(id);
            if (user.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Lấy user thành công", user.get()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Không tìm thấy user với id: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Lỗi khi lấy user: " + e.getMessage()));
        }
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) {
        try {
            // Kiểm tra username đã tồn tại chưa
            Optional<User> existingUser = userService.findByUsername(user.getUsername());
            if (existingUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Username đã tồn tại"));
            }
            
            User savedUser = userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Tạo user thành công", savedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Lỗi khi tạo user: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody User user) {
        try {
            Optional<User> existingUser = userService.findById(id);
            if (existingUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Không tìm thấy user với id: " + id));
            }
            
            // Đảm bảo id từ path variable được sử dụng, ignore id từ body nếu có
            user.setId(id);
            
            // Kiểm tra username đã tồn tại chưa (trừ user hiện tại)
            Optional<User> userWithSameUsername = userService.findByUsername(user.getUsername());
            if (userWithSameUsername.isPresent() && userWithSameUsername.get().getId() != id) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Username đã tồn tại"));
            }
            
            User updatedUser = userService.update(id, user);
            return ResponseEntity.ok(ApiResponse.success("Cập nhật user thành công", updatedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Lỗi khi cập nhật user: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable Long id) {
        try {
            Optional<User> user = userService.findById(id);
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Không tìm thấy user với id: " + id));
            }
            userService.deleteById(id);
            return ResponseEntity.ok(ApiResponse.success("Xóa user thành công", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Lỗi khi xóa user: " + e.getMessage()));
        }
    }
}
