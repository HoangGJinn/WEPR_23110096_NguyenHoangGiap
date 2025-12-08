package com.giap.service;

import com.giap.entity.Category;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    List<Category> findAll(Sort sort);
    Optional<Category> findById(Long id);
    Optional<Category> findByCategoryName(String name);
    Category save(Category category);
    Category update(Long id, Category category);
    void delete(Category category);
    void deleteById(Long id);
    long count();
    <S extends Category> Optional<S> findOne(Example<S> example);
    List<Category> findAllById(Iterable<Long> iterable);
    Page<Category> findAll(Pageable pageable);
    Page<Category> findByCategoryNameContaining(String name, Pageable pageable);
    List<Category> findByCategoryNameContaining(String name);


    List<Category> findByUser(Long userId);
    List<Category> findByStatus(Boolean status);
}

