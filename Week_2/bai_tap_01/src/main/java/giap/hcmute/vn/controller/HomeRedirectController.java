package giap.hcmute.vn.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet({"", "/"})
public class HomeRedirectController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy session nếu đã tồn tại, không tạo mới
        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("account") != null) {
            // Đã login -> chuyển tới waiting
            resp.sendRedirect(req.getContextPath() + "/waiting");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/login");
        return;
    }
}
