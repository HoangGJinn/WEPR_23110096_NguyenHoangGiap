package com.giap.service.impl;

import com.giap.entity.Category;
import com.giap.entity.User;
import com.giap.repository.CategoryRepository;
import com.giap.repository.UserRepository;
import com.giap.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll(Sort sort) {
        return categoryRepository.findAll(sort);
    }

    @Override
    public Optional<Category> findByCategoryName(String name) {
        return categoryRepository.findByCategoryName(name);
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public long count() {
        return categoryRepository.count();
    }

    @Override
    public <S extends Category> Optional<S> findOne(Example<S> example) {
        return categoryRepository.findOne(example);
    }

    @Override
    public List<Category> findAllById(Iterable<Long> iterable) {
        return categoryRepository.findAllById(iterable);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Page<Category> findByCategoryNameContaining(String name, Pageable pageable) {
        return categoryRepository.findByCategoryNameContaining(name, pageable);
    }

    @Override
    public List<Category> findByCategoryNameContaining(String name) {
        return categoryRepository.findByCategoryNameContaining(name);
    }

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    
    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isPresent()) {
            Category categoryToUpdate = existingCategory.get();
            categoryToUpdate.setCategoryName(category.getCategoryName());
            categoryToUpdate.setCategoryCode(category.getCategoryCode());
            categoryToUpdate.setImages(category.getImages());
            categoryToUpdate.setStatus(category.getStatus());
            if (category.getUser() != null) {
                categoryToUpdate.setUser(category.getUser());
            }
            return categoryRepository.save(categoryToUpdate);
        }
        throw new RuntimeException("Category not found with id: " + id);
    }
    
    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
    
    @Override
    public List<Category> findByUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return categoryRepository.findByUser(user.get());
        }
        return List.of();
    }
    
    @Override
    public List<Category> findByStatus(Boolean status) {
        return categoryRepository.findByStatus(status);
    }
}

