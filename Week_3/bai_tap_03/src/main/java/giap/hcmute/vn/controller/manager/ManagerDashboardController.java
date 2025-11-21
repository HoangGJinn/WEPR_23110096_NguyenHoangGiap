package giap.hcmute.vn.controller.manager;

import java.io.IOException;
import java.util.List;

import giap.hcmute.vn.dto.CategoryDTO;
import giap.hcmute.vn.dto.UserDTO;
import giap.hcmute.vn.service.CategoryService;
import giap.hcmute.vn.service.impl.CategoryServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/manager/dashboard")
public class ManagerDashboardController extends HttpServlet {

    private final CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Kiểm tra session và role manager
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("account");

        // Kiểm tra quyền manager (roleid = 2)
        if (user.getRoleid() != 2) {
            session.setAttribute("errorMessage", "Bạn không có quyền truy cập trang này.");
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        // Lấy danh sách category của manager này
        List<CategoryDTO> categories = categoryService.getByUserId(user.getId());
        req.setAttribute("categories", categories);
        req.setAttribute("categoryCount", categories.size());

        // Forward đến trang manager home
        req.getRequestDispatcher("/WEB-INF/views/manager/dashboard.jsp").forward(req, resp);
    }
}
