package com.giap.baitap08.interceptor;

import com.giap.baitap08.entity.UserRole;
import com.giap.baitap08.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        // Các trang public không cần đăng nhập
        if (uri.equals("/login") || uri.equals("/register") || 
            uri.startsWith("/css") || uri.startsWith("/js") || 
            uri.startsWith("/images") || uri.startsWith("/graphiql") || 
            uri.startsWith("/graphql")) {
            return true;
        }

        // Lấy JWT token từ request
        String token = getTokenFromRequest(request);

        // Kiểm tra đăng nhập
        if (token == null || !jwtUtil.validateToken(token)) {
            response.sendRedirect("/login");
            return false;
        }

        // Lấy role từ token
        UserRole userRole;
        try {
            userRole = jwtUtil.extractRole(token);
        } catch (Exception e) {
            response.sendRedirect("/login");
            return false;
        }

        // Các trang chỉ dành cho ADMIN
        if (uri.startsWith("/users") || uri.startsWith("/categories") || uri.startsWith("/products")) {
            if (userRole != UserRole.ADMIN) {
                response.sendRedirect("/?error=unauthorized");
                return false;
            }
        }

        // USER chỉ có thể truy cập trang chủ (/) và logout
        if (userRole == UserRole.USER) {
            if (!uri.equals("/") && !uri.equals("/logout")) {
                response.sendRedirect("/?error=unauthorized");
                return false;
            }
        }

        return true;
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        // Try to get token from cookie first
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        // Try to get token from Authorization header
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
