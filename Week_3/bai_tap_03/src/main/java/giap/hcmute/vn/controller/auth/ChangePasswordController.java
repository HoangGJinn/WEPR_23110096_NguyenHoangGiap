package giap.hcmute.vn.controller.auth;

import java.io.IOException;

import giap.hcmute.vn.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import giap.hcmute.vn.service.impl.UserServiceImpl;

@WebServlet("/changepassword")
public class ChangePasswordController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        req.setAttribute("email", email);
        req.getRequestDispatcher("/WEB-INF/views/changepassword.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        // Validation
        if (newPassword == null || newPassword.trim().isEmpty()) {
            req.setAttribute("alert", "Vui lòng nhập mật khẩu mới.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/WEB-INF/views/changepassword.jsp").forward(req, resp);
            return;
        }

        if (confirmPassword == null || !newPassword.equals(confirmPassword)) {
            req.setAttribute("alert", "Mật khẩu mới và xác nhận mật khẩu không khớp.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/WEB-INF/views/changepassword.jsp").forward(req, resp);
            return;
        }

        try{
            UserService userService = new UserServiceImpl();
            userService.updatePasswordByEmail(email, newPassword);

            // Lưu message vào session
            req.getSession().setAttribute("successMessage", "Đổi mật khẩu thành công! Vui lòng đăng nhập với mật khẩu mới.");

            // Redirect to login page after successful password change
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi để debug
            req.setAttribute("alert", "Đã xảy ra lỗi khi đổi mật khẩu. Vui lòng thử lại.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/WEB-INF/views/changepassword.jsp").forward(req, resp);
        }
    }
}
