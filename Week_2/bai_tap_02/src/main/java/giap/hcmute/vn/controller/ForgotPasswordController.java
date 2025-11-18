package giap.hcmute.vn.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import giap.hcmute.vn.service.impl.UserServiceImpl;

@WebServlet("/forgotpassword")
public class ForgotPasswordController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/forgotpassword.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");

        UserServiceImpl userService = new UserServiceImpl();

        // Kiểm tra email có tồn tại không
        if (!userService.checkExistEmail(email)) {
            req.setAttribute("alert", "Email không tồn tại trong hệ thống.");
            req.getRequestDispatcher("/WEB-INF/views/forgotpassword.jsp").forward(req, resp);
            return;
        }

        // Change password
        resp.sendRedirect(req.getContextPath() + "/changepassword?email=" + email);
    }
}
