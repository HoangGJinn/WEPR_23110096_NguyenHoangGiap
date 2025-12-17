package com.giap.baitap08.interceptor;

import com.giap.baitap08.entity.User;
import com.giap.baitap08.entity.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession(false);
        User loggedInUser = null;

        if (session != null) {
            loggedInUser = (User) session.getAttribute("loggedInUser");
        }

        // Các trang public không cần đăng nhập
        if (uri.equals("/login") || uri.equals("/register") || 
            uri.startsWith("/css") || uri.startsWith("/js") || 
            uri.startsWith("/images") || uri.startsWith("/graphiql") || 
            uri.startsWith("/graphql")) {
            return true;
        }

        // Kiểm tra đăng nhập
        if (loggedInUser == null) {
            response.sendRedirect("/login");
            return false;
        }

        // Các trang chỉ dành cho ADMIN
        if (uri.startsWith("/users") || uri.startsWith("/categories") || uri.startsWith("/products")) {
            if (loggedInUser.getRole() != UserRole.ADMIN) {
                response.sendRedirect("/?error=unauthorized");
                return false;
            }
        }

        // USER chỉ có thể truy cập trang chủ (/) và logout
        if (loggedInUser.getRole() == UserRole.USER) {
            if (!uri.equals("/") && !uri.equals("/logout")) {
                response.sendRedirect("/?error=unauthorized");
                return false;
            }
        }

        return true;
    }
}
