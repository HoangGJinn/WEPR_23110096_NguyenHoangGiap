package com.giap.service;

import com.giap.entity.Video;
import java.util.List;
import java.util.Optional;

public interface VideoService {
    List<Video> findAll();
    Optional<Video> findById(Long id);
    Video save(Video video);
    Video update(Long id, Video video);
    void deleteById(Long id);
    List<Video> findByCategory(Long categoryId);
    List<Video> findByActive(Boolean active);
}

