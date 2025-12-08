package com.giap.controller;

import com.giap.entity.Category;
import com.giap.entity.Video;
import com.giap.service.CategoryService;
import com.giap.service.VideoService;
import com.giap.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/videos")
public class VideoController {
    
    @Autowired
    private VideoService videoService;
    
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Video>>> getAllVideos(
            @RequestParam(required = false) String search) {
        try {
            List<Video> videos;
            if (search != null && !search.trim().isEmpty()) {
                videos = videoService.findAll().stream()
                    .filter(v -> v.getTitle().toLowerCase().contains(search.toLowerCase()) ||
                               (v.getDescription() != null && v.getDescription().toLowerCase().contains(search.toLowerCase())))
                    .toList();
            } else {
                videos = videoService.findAll();
            }
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách video thành công", videos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Lỗi khi lấy danh sách video: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Video>> getVideoById(@PathVariable Long id) {
        try {
            Optional<Video> video = videoService.findById(id);
            if (video.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Lấy video thành công", video.get()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Không tìm thấy video với id: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Lỗi khi lấy video: " + e.getMessage()));
        }
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<Video>> createVideo(
            @Valid @RequestBody Video video,
            @RequestParam(required = false) Long categoryId) {
        try {
            if (categoryId != null) {
                Optional<Category> category = categoryService.findById(categoryId);
                if (category.isPresent()) {
                    video.setCategory(category.get());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(ApiResponse.error("Không tìm thấy category với id: " + categoryId));
                }
            }
            
            // Set createdAt nếu chưa có
            if (video.getCreatedAt() == null) {
                video.setCreatedAt(LocalDateTime.now());
            }
            
            Video savedVideo = videoService.save(video);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Tạo video thành công", savedVideo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Lỗi khi tạo video: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Video>> updateVideo(
            @PathVariable Long id,
            @Valid @RequestBody Video video) {
        try {
            Optional<Video> existingVideo = videoService.findById(id);
            if (existingVideo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Không tìm thấy video với id: " + id));
            }
            
            // Đảm bảo id từ path variable được sử dụng, ignore id từ body nếu có
            video.setId(id);
            
            // Nếu body không có category, giữ nguyên category hiện tại
            if (video.getCategory() == null) {
                video.setCategory(existingVideo.get().getCategory());
            }
            
            Video updatedVideo = videoService.update(id, video);
            return ResponseEntity.ok(ApiResponse.success("Cập nhật video thành công", updatedVideo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Lỗi khi cập nhật video: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteVideo(@PathVariable Long id) {
        try {
            Optional<Video> video = videoService.findById(id);
            if (video.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Không tìm thấy video với id: " + id));
            }
            videoService.deleteById(id);
            return ResponseEntity.ok(ApiResponse.success("Xóa video thành công", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Lỗi khi xóa video: " + e.getMessage()));
        }
    }
    
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<Video>>> getVideosByCategory(@PathVariable Long categoryId) {
        try {
            List<Video> videos = videoService.findByCategory(categoryId);
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách video theo category thành công", videos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Lỗi khi lấy danh sách video: " + e.getMessage()));
        }
    }
}
