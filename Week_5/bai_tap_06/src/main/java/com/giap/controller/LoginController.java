package com.giap.controller;

import com.giap.config.SecurityConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String loginPage(
			@RequestParam(required = false) String error,
			@RequestParam(required = false) String logout,
			Model model) {
		
		if (error != null) {
			model.addAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
		}
		
		if (logout != null) {
			model.addAttribute("message", "Bạn đã đăng xuất thành công!");
		}
		
		// Hiển thị thông tin đăng nhập trên trang
		model.addAttribute("username", SecurityConfig.getUsername());
		model.addAttribute("password", SecurityConfig.getPassword());
		
		return "login";
	}
}

