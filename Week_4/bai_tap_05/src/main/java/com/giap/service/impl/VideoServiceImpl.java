package com.giap.service.impl;

import com.giap.entity.Category;
import com.giap.entity.Video;
import com.giap.repository.CategoryRepository;
import com.giap.repository.VideoRepository;
import com.giap.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {
    
    @Autowired
    private VideoRepository videoRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override
    public List<Video> findAll() {
        return videoRepository.findAll();
    }
    
    @Override
    public Optional<Video> findById(Long id) {
        return videoRepository.findById(id);
    }
    
    @Override
    public Video save(Video video) {
        return videoRepository.save(video);
    }
    
    @Override
    public Video update(Long id, Video video) {
        Optional<Video> existingVideo = videoRepository.findById(id);
        if (existingVideo.isPresent()) {
            Video videoToUpdate = existingVideo.get();
            videoToUpdate.setTitle(video.getTitle());
            videoToUpdate.setPoster(video.getPoster());
            videoToUpdate.setViews(video.getViews());
            videoToUpdate.setDescription(video.getDescription());
            videoToUpdate.setActive(video.getActive());
            if (video.getCategory() != null) {
                videoToUpdate.setCategory(video.getCategory());
            }
            return videoRepository.save(videoToUpdate);
        }
        throw new RuntimeException("Video not found with id: " + id);
    }
    
    @Override
    public void deleteById(Long id) {
        videoRepository.deleteById(id);
    }
    
    @Override
    public List<Video> findByCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            return videoRepository.findByCategory(category.get());
        }
        return List.of();
    }
    
    @Override
    public List<Video> findByActive(Boolean active) {
        return videoRepository.findByActive(active);
    }
}

