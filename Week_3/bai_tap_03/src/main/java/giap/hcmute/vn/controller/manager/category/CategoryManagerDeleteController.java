package giap.hcmute.vn.controller.manager.category;

import java.io.IOException;

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

@WebServlet("/manager/category/delete")
public class CategoryManagerDeleteController extends HttpServlet {
    CategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Kiểm tra session
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("account") == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            UserDTO currentUser = (UserDTO) session.getAttribute("account");

            // Kiểm tra quyền manager
            if (currentUser.getRoleid() != 2) {
                session.setAttribute("errorMessage", "Bạn không có quyền truy cập trang này.");
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            }

            String id = req.getParameter("id");

            if (id == null || id.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing Category id");
                return;
            }

            // Kiểm tra quyền sở hữu
            CategoryDTO category = cateService.get(Integer.parseInt(id));
            if (category == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Category not found");
                return;
            }

            if (category.getUserId() != currentUser.getId()) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền xóa category này");
                return;
            }

            // Xóa category
            cateService.delete(Integer.parseInt(id));

            // Redirect về list
            resp.sendRedirect(req.getContextPath() + "/manager/category/list");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Category id format");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting Category: " + e.getMessage());
        }
    }
}
