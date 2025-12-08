package com.giap.repository;

import com.giap.entity.Category;
import com.giap.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByCategory(Category category);
    List<Video> findByActive(Boolean active);

    //find by title contains
    List<Video> findByTitleContains(String title);
    //find and paging
    Page<Video> findByTitleContains(String title, Pageable pageable);

    Optional<Video> findByTitle(String title);
    List<Video> findByCreatedAt(LocalDateTime createdAt);
}
