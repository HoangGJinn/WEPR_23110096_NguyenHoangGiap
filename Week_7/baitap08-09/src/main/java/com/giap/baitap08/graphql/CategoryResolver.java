package com.giap.baitap08.graphql;

import com.giap.baitap08.entity.Category;
import com.giap.baitap08.entity.User;
import com.giap.baitap08.repository.CategoryRepository;
import com.giap.baitap08.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryResolver {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @QueryMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @QueryMapping
    public Category getCategoryById(@Argument Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @MutationMapping
    public Category createCategory(@Argument String name, @Argument String images) {
        Category category = new Category();
        category.setName(name);
        category.setImages(images);
        return categoryRepository.save(category);
    }

    @MutationMapping
    public Category updateCategory(@Argument Long id, @Argument String name, @Argument String images) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        if (name != null) category.setName(name);
        if (images != null) category.setImages(images);
        return categoryRepository.save(category);
    }

    @MutationMapping
    public Boolean deleteCategory(@Argument Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

