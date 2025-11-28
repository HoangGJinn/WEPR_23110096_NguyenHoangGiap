package com.giap.repository;

import com.giap.entity.Category;
import com.giap.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByCategory(Category category);
    List<Video> findByActive(Boolean active);
}
