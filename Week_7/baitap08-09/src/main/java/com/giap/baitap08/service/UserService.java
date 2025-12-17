package com.giap.baitap08.service;

import com.giap.baitap08.dto.UserLoginDTO;
import com.giap.baitap08.dto.UserRegistrationDTO;
import com.giap.baitap08.entity.User;
import com.giap.baitap08.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    @Transactional
    public User registerUser(UserRegistrationDTO registrationDTO) throws Exception {
        // Kiểm tra email đã tồn tại
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new Exception("Email đã được sử dụng");
        }
        
        // Kiểm tra mật khẩu khớp
        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            throw new Exception("Mật khẩu xác nhận không khớp");
        }
        
        // Tạo user mới
        User user = new User();
        user.setFullname(registrationDTO.getFullname());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(registrationDTO.getPassword()); // Trong thực tế nên mã hóa password
        user.setPhone(registrationDTO.getPhone());
        
        return userRepository.save(user);
    }
    
    public User loginUser(UserLoginDTO loginDTO) throws Exception {
        Optional<User> userOptional = userRepository.findByEmail(loginDTO.getEmail());
        
        if (userOptional.isEmpty()) {
            throw new Exception("Email hoặc mật khẩu không chính xác");
        }
        
        User user = userOptional.get();
        
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new Exception("Email hoặc mật khẩu không chính xác");
        }
        
        return user;
    }
}
