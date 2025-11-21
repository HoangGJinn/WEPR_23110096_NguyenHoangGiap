package giap.hcmute.vn.controller.auth;

import java.io.IOException;

import giap.hcmute.vn.constant.Constant;
import giap.hcmute.vn.dto.UserDTO;
import giap.hcmute.vn.service.UserService;
import giap.hcmute.vn.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Nếu đã có session account thì chuyển tới trang chờ
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute(Constant.SESSION_ACCOUNT) != null) {
            resp.sendRedirect(req.getContextPath() + "/waiting");
            return;
        }

        // Lấy và xóa success message từ session (nếu có)
        if (session != null) {
            String successMessage = (String) session.getAttribute("successMessage");
            if (successMessage != null) {
                req.setAttribute("successMessage", successMessage);
                session.removeAttribute("successMessage"); // Xóa ngay sau khi lấy
            }
        }

        // Kiểm tra cookie "remember username"
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (Constant.COOKIE_REMEMBER.equals(c.getName())) {
                    String username = c.getValue();
                    if (username != null && !username.isBlank()) {
                        // Lấy UserDTO từ DB theo username và lưu vào session (nếu tồn tại)
                        UserDTO UserDTO = userService.get(username);
                        if (UserDTO != null) {
                            session = req.getSession(true);
                            session.setAttribute(Constant.SESSION_ACCOUNT, UserDTO);
                            resp.sendRedirect(req.getContextPath() + "/waiting");
                            return;
                        }
                    }
                }
            }
        }

        // Nếu chưa login -> forward tới trang login.jsp
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Thiết lập encoding cho form
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
        boolean isRememberMe = "on".equals(remember);
        UserDTO loggedUser = userService.login(username, password);

        String alertMsg = "";

        // Kiểm tra rỗng
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            alertMsg = "Tài khoản hoặc mật khẩu không được rỗng";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        // Gọi service để kiểm tra login
        UserDTO user = userService.login(username, password);
        if (user != null) {
            // Lưu UserDTO vào session
            HttpSession session = req.getSession(true);
            session.setAttribute(Constant.SESSION_ACCOUNT, user);
            req.getSession().setAttribute("currentUser", loggedUser);

            // Lưu cookie nếu chọn "remember me"
            if (isRememberMe) {
                saveRememberMe(req, resp, username);
            }

            // Chuyển tới trang chờ/landing
            resp.sendRedirect(req.getContextPath() + "/waiting");
            return;
        } else {
            // Đăng nhập thất bại
            alertMsg = "Tài khoản hoặc mật khẩu không đúng";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }
    }

    /**
     * Lưu cookie nhớ username
     * - Đặt path bằng context path để cookie có hiệu lực đúng dự án
     * - Thời gian sống: 30 phút (ví dụ)
     */
    private void saveRememberMe(HttpServletRequest req, HttpServletResponse resp, String username) {
        Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);
        // 30 phút = 30 * 60 (giây)
        cookie.setMaxAge(30 * 60);
        // Optional: chỉ cookie cho ứng dụng này
        String contextPath = req.getContextPath();
        if (contextPath == null || contextPath.isBlank()) {
            contextPath = "/";
        }
        cookie.setPath(contextPath);
        resp.addCookie(cookie);
    }
}
