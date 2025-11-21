package giap.hcmute.vn.controller.manager.category;

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

@WebServlet("/manager/category/edit")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class CategoryManagerEditController extends HttpServlet {
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

            CategoryDTO category = cateService.get(Integer.parseInt(id));

            if (category == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Category not found");
                return;
            }

            // Kiểm tra xem category có thuộc về manager này không
            if (category.getUserId() != currentUser.getId()) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền chỉnh sửa category này");
                return;
            }

            req.setAttribute("cate", category);
            req.getRequestDispatcher("/WEB-INF/views/manager/category/edit-category.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Category id format");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading Category: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html;charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");

            // Kiểm tra session
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("account") == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            UserDTO currentUser = (UserDTO) session.getAttribute("account");

            // Kiểm tra quyền manager
            if (currentUser.getRoleid() != 2) {
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            }

            // Lấy form data
            String categoryId = req.getParameter("id");
            String categoryName = req.getParameter("name");

            // Kiểm tra quyền sở hữu
            CategoryDTO existingCategory = cateService.get(Integer.parseInt(categoryId));
            if (existingCategory == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Category not found");
                return;
            }

            if (existingCategory.getUserId() != currentUser.getId()) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền chỉnh sửa category này");
                return;
            }

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

                // Set fileName với prefix "categories/"
                fileName = "categories/" + uniqueFileName;
            }

            // Tạo CategoryDTO mới để update
            CategoryDTO updateDto = new CategoryDTO();
            updateDto.setCateid(Integer.parseInt(categoryId));
            updateDto.setCatename(categoryName);
            updateDto.setUserId(currentUser.getId()); // Giữ nguyên userId

            if (fileName != null) {
                updateDto.setIcon(fileName);
            } else {
                updateDto.setIcon(existingCategory.getIcon());
            }

            // Update
            cateService.edit(updateDto);

            // Redirect về list
            resp.sendRedirect(req.getContextPath() + "/manager/category/list");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi khi cập nhật category: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/manager/category/edit-category.jsp").forward(req, resp);
        }
    }
}
