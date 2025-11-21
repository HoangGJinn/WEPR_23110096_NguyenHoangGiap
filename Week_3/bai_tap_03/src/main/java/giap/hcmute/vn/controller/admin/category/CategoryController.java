package giap.hcmute.vn.controller.admin.category;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;

import giap.hcmute.vn.dto.CategoryDTO;
import giap.hcmute.vn.dto.UserDTO;
import giap.hcmute.vn.service.CategoryService;
import giap.hcmute.vn.service.impl.CategoryServiceImpl;

@WebServlet("/admin/category/list")
public class CategoryController extends HttpServlet {
    private final CategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            HttpSession session = req.getSession(false);
            UserDTO currentUser = (UserDTO) (session != null ? session.getAttribute("account") : null);

            if (currentUser == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
            if (currentUser.getRoleid() != 1) {
                req.setAttribute("errorMessage", "Bạn không có quyền truy cập trang này.");
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            }

        List<CategoryDTO> cateList = cateService.getAll();
        req.setAttribute("cateList", cateList);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/admin/category/list-category.jsp");
        dispatcher.forward(req, resp);
    }
}
