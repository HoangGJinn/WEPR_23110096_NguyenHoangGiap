package giap.hcmute.vn.controller.admin.category;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import giap.hcmute.vn.constant.Constant;
import giap.hcmute.vn.dto.CategoryDTO;
import giap.hcmute.vn.dto.UserDTO;
import giap.hcmute.vn.service.CategoryService;
import giap.hcmute.vn.service.impl.CategoryServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/admin/category/add")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class CategoryAddController extends HttpServlet{
    CategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/admin/category/add-category.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html;charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");

            HttpSession session = req.getSession(false);
            // Kiểm tra session và lấy user
            UserDTO currentUser = (UserDTO) (session != null ? session.getAttribute("account") : null);
            if (currentUser == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
            if (currentUser.getRoleid() != 1) {
                session.setAttribute("errorMessage", "Bạn không có quyền truy cập trang này.");
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            }

            CategoryDTO category = new CategoryDTO();

            // Lấy tên category
            String categoryName = req.getParameter("name");
            category.setCatename(categoryName);

            // Lấy id user và thêm vào categoryDTO
            category.setUserId(currentUser.getId());


            // Xử lý file upload
            Part filePart = req.getPart("icon");

            if (filePart != null && filePart.getSize() > 0) {
                // Lấy tên file gốc và extension
                String originalFileName = Paths.get(filePart.getSubmittedFileName())
                                                .getFileName().toString();
                int index = originalFileName.lastIndexOf(".");
                String ext = index > 0 ? originalFileName.substring(index) : "";

                // Tên file = timestamp + extension
                String fileName = System.currentTimeMillis() + ext;

                // Đường dẫn lưu file vào D:/uploads/categories
                String uploadPath = Constant.UPLOAD_PATH + "uploads" + File.separator + "categories";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Lưu file
                Path filePath = Paths.get(uploadPath, fileName);
                try (var inputStream = filePart.getInputStream()) {
                    // Files.copy(source, target, options):
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                }

                // Set icon với prefix "categories/"
                category.setIcon("categories/" + fileName);
            }

            // Insert category
            cateService.insert(category);

            // Redirect
            resp.sendRedirect(req.getContextPath() + "/admin/category/list");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi khi thêm category: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/admin/category/add-category.jsp").forward(req, resp);
        }
    }
}
