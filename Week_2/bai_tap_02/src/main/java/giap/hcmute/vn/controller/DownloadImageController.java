package giap.hcmute.vn.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/image") // ?fname=abc.png
public class DownloadImageController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String fileName = req.getParameter("fname");
        
        // Validate filename để tránh path traversal attack
        if (fileName == null || fileName.contains("..")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid filename");
            return;
        }
        
        // Resolve upload directory from servlet context instead of using Constant.DIR
        String uploadsDir = getServletContext().getRealPath("/uploads");
        if (uploadsDir == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Upload directory not available");
            return;
        }
        File file = new File(uploadsDir, fileName);
        
        // Kiểm tra file tồn tại và là file (không phải folder)
        if (!file.exists() || !file.isFile()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
            return;
        }
        
        // Tự động detect content type dựa vào extension
        String contentType = getServletContext().getMimeType(fileName);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        resp.setContentType(contentType);
        
        // Set thêm header cho caching
        resp.setHeader("Cache-Control", "max-age=3600"); // Cache 1 giờ
        
        // Copy file using NIO
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}
