package com.giap.baitap08.controller;

import com.giap.baitap08.dto.UserLoginDTO;
import com.giap.baitap08.dto.UserRegistrationDTO;
import com.giap.baitap08.entity.User;
import com.giap.baitap08.repository.ProductRepository;
import com.giap.baitap08.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final UserService userService;
    private final ProductRepository productRepository;

    @ModelAttribute("avatarUrl")
    public String getAvatarUrl() {
        return "/images/profile.jpg";
    }

    @ModelAttribute("userName")
    public String getUserName(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        return user != null ? user.getFullname() : "Guest";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

    @GetMapping("/products")
    public String products() {
        return "products";
    }

    @GetMapping("/users")
    public String users() {
        return "users";
    }

    @GetMapping("/categories")
    public String categories() {
        return "categories";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userRegistrationDTO", new UserRegistrationDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserRegistrationDTO userRegistrationDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.registerUser(userRegistrationDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userLoginDTO", new UserLoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@Valid @ModelAttribute UserLoginDTO userLoginDTO,
                          BindingResult bindingResult,
                          HttpSession session,
                          RedirectAttributes redirectAttributes,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        try {
            User user = userService.loginUser(userLoginDTO);
            session.setAttribute("loggedInUser", user);
            redirectAttributes.addFlashAttribute("successMessage", "Đăng nhập thành công!");
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("successMessage", "Đăng xuất thành công!");
        return "redirect:/login";
    }
}

