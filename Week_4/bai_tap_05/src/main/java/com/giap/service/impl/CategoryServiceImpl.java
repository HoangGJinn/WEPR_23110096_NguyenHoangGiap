package com.giap.service.impl;

import com.giap.entity.Category;
import com.giap.entity.User;
import com.giap.repository.CategoryRepository;
import com.giap.repository.UserRepository;
import com.giap.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
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

