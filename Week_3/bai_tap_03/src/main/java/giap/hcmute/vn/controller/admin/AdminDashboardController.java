package giap.hcmute.vn.controller.admin;

import java.io.IOException;

import giap.hcmute.vn.dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/admin/dashboard")
public class AdminDashboardController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Kiểm tra session và role admin
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("account");

        // Kiểm tra quyền admin (roleid = 1)
        if (user.getRoleid() != 1) {
            session.setAttribute("errorMessage", "Bạn không có quyền truy cập trang này.");
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        // Forward đến trang admin home
        req.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(req, resp);
    }
}
