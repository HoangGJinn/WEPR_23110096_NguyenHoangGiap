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
public class UserResolver {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @QueryMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @QueryMapping
    public User getUserById(@Argument Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @MutationMapping
    public User createUser(@Argument String fullname, @Argument String email, 
                          @Argument String password, @Argument String phone) {
        User user = new User();
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        return userRepository.save(user);
    }

    @MutationMapping
    public User updateUser(@Argument Long id, @Argument String fullname, 
                          @Argument String email, @Argument String password, 
                          @Argument String phone) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (fullname != null) user.setFullname(fullname);
        if (email != null) user.setEmail(email);
        if (password != null) user.setPassword(password);
        if (phone != null) user.setPhone(phone);
        return userRepository.save(user);
    }

    @MutationMapping
    public Boolean deleteUser(@Argument Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @MutationMapping
    public User addCategoryToUser(@Argument Long userId, @Argument Long categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        user.getCategories().add(category);
        return userRepository.save(user);
    }

    @MutationMapping
    public User removeCategoryFromUser(@Argument Long userId, @Argument Long categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        user.getCategories().remove(category);
        return userRepository.save(user);
    }
}

