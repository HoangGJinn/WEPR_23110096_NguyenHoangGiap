package giap.hcmute.vn.controller.admin.category;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import giap.hcmute.vn.constant.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import giap.hcmute.vn.dto.CategoryDTO;
import giap.hcmute.vn.dto.UserDTO;
import giap.hcmute.vn.service.CategoryService;
import giap.hcmute.vn.service.impl.CategoryServiceImpl;

@WebServlet("/admin/category/edit")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class CategoryEditController extends HttpServlet {
    CategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id = req.getParameter("id");
            HttpSession session = req.getSession(false);
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


            CategoryDTO category = cateService.get(Integer.parseInt(id));

            if (category == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Category not found");
                return;
            }

            req.setAttribute("cate", category);
            req.getRequestDispatcher("/WEB-INF/views/admin/category/edit-category.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid CategoryDTO id format");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading CategoryDTO: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html;charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");

            // Lấy form data
            String categoryId = req.getParameter("id");
            String categoryName = req.getParameter("name");

            // Lấy file upload
            Part filePart = req.getPart("icon");
            String fileName = null;

            if (filePart != null && filePart.getSize() > 0) {
                // Lấy tên file gốc và extension
                String originalFileName = Paths.get(filePart.getSubmittedFileName())
                                                .getFileName().toString();
                int index = originalFileName.lastIndexOf(".");
                String ext = index > 0 ? originalFileName.substring(index) : "";

                // Tên file = timestamp + extension
                String uniqueFileName = System.currentTimeMillis() + ext;

                // Đường dẫn lưu file vào D:/uploads/categories
                String uploadPath = Constant.UPLOAD_PATH + "uploads" + File.separator + "categories";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Lưu file
                Path filePath = Paths.get(uploadPath, uniqueFileName);
                try (var inputStream = filePart.getInputStream()) {
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                }

                // Set fileName với prefix "category/"
                fileName = "category/" + uniqueFileName;
            }

            // Update Category
            CategoryDTO category = cateService.get(Integer.parseInt(categoryId));
            category.setCatename(categoryName);

            if (fileName != null) {
                category.setIcon(fileName);
            }

            cateService.edit(category);

            // Redirect
            resp.sendRedirect(req.getContextPath() + "/admin/category/list");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi khi cập nhật category: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/admin/category/edit-category.jsp").forward(req, resp);
        }
    }
}
