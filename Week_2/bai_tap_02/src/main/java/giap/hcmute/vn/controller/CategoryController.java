package giap.hcmute.vn.controller;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;

import giap.hcmute.vn.model.Category;
import giap.hcmute.vn.service.CategoryService;
import giap.hcmute.vn.service.impl.CategoryServiceImpl;

@WebServlet("/admin/category/list")
public class CategoryController extends HttpServlet {
    private final CategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> cateList = cateService.getAll();
        req.setAttribute("cateList", cateList);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/admin/category/list-category.jsp");
        dispatcher.forward(req, resp);
    }
}
