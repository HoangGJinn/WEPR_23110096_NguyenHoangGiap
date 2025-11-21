package giap.hcmute.vn.controller.common;
import giap.hcmute.vn.dto.CategoryDTO;
import giap.hcmute.vn.model.CategoryEntity;
import giap.hcmute.vn.service.CategoryService;
import giap.hcmute.vn.service.impl.CategoryServiceImpl;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeController extends HttpServlet{

	private final CategoryService categoryService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lấy error message từ session (nếu có)
		String errorMessage = (String) req.getSession().getAttribute("errorMessage");
		if (errorMessage != null) {
			req.setAttribute("errorMessage", errorMessage);
			req.getSession().removeAttribute("errorMessage"); // Xóa sau khi lấy
		}

		// Lấy success message từ session (nếu có)
		String successMessage = (String) req.getSession().getAttribute("successMessage");
		if (successMessage != null) {
			req.setAttribute("successMessage", successMessage);
			req.getSession().removeAttribute("successMessage");
		}

		List<CategoryDTO> categories = categoryService.getAll();
		req.setAttribute("categories", categories);
		req.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(req, resp);
	}
}
