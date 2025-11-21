package giap.hcmute.vn.controller.manager.category;

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

@WebServlet("/manager/category/list")
public class CategoryManagerController extends HttpServlet {
    private final CategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Kiểm tra session
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        UserDTO currentUser = (UserDTO) session.getAttribute("account");

        // Kiểm tra quyền manager (roleid = 2)
        if (currentUser.getRoleid() != 2) {
        	session.setAttribute("errorMessage", "Bạn không có quyền truy cập trang này.");
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        // Lấy danh sách category của manager này
        List<CategoryDTO> cateList = cateService.getByUserId(currentUser.getId());
        req.setAttribute("cateList", cateList);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/manager/category/list-category.jsp");
        dispatcher.forward(req, resp);
    }
}
