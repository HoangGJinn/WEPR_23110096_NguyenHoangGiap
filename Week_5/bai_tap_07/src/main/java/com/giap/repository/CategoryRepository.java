package com.giap.repository;

import com.giap.entity.Category;
import com.giap.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUser(User user);
    List<Category> findByStatus(Boolean status);
    
    Optional<Category> findByCategoryName(String name);
    
    // find by name containing
    List<Category> findByCategoryNameContaining(String categoryName);
    // find and paging
    Page<Category> findByCategoryNameContaining(String categoryName, Pageable pageable);
}
