package com.giap.service;

import com.giap.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    User update(Long id, User user);
    void deleteById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}