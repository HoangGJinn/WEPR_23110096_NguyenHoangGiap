package giap.hcmute.vn.controller;

import java.io.IOException;

import giap.hcmute.vn.constant.Constant;
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
@WebServlet("/register")
public class RegisterController extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Nếu đã có session account thì chuyển tới admin/home (hoặc waiting)
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            resp.sendRedirect(req.getContextPath() + "/waiting");
            return;
        }

        // Nếu có cookie remember username thì cũng có thể auto-redirect (tuỳ logic)
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Constant.COOKIE_REMEMBER.equals(cookie.getName())) {
                    // Optionally: bạn có thể lấy user từ DB và login tự động
                    // nhưng ở đây ta chỉ redirect tới waiting nếu thực sự muốn auto-login
                }
            }
        }

        // Hiển thị form đăng ký
        req.getRequestDispatcher(Constant.REGISTER).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Encoding
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        // Lấy params từ form
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String repassword = req.getParameter("repassword"); // nếu có
        String email = req.getParameter("email");
        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        String remember = req.getParameter("remember"); // nếu có checkbox

        String alert = null;

        // Validate cơ bản
        if (username == null || username.isBlank()
                || password == null || password.isBlank()
                || email == null || email.isBlank()) {
            alert = "Username, password và email không được để trống.";
            req.setAttribute("alert", alert);
            req.getRequestDispatcher(Constant.REGISTER).forward(req, resp);
            return;
        }

        if (repassword != null && !password.equals(repassword)) {
            alert = "Mật khẩu và xác nhận mật khẩu không khớp.";
            req.setAttribute("alert", alert);
            req.getRequestDispatcher(Constant.REGISTER).forward(req, resp);
            return;
        }

        // Kiểm tra tồn tại username / email / phone
        if (userService.checkExistUsername(username)) {
            alert = "Tên đăng nhập đã tồn tại.";
            req.setAttribute("alert", alert);
            req.getRequestDispatcher(Constant.REGISTER).forward(req, resp);
            return;
        }
        if (email != null && userService.checkExistEmail(email)) {
            alert = "Email đã được sử dụng.";
            req.setAttribute("alert", alert);
            req.getRequestDispatcher(Constant.REGISTER).forward(req, resp);
            return;
        }
        if (phone != null && !phone.isBlank() && userService.checkExistPhone(phone)) {
            alert = "Số điện thoại đã được sử dụng.";
            req.setAttribute("alert", alert);
            req.getRequestDispatcher(Constant.REGISTER).forward(req, resp);
            return;
        }

        // Gọi service để tạo user
        boolean created = userService.register(username, password, email, fullname, phone);
        if (!created) {
            alert = "Đăng ký thất bại (có thể username đã tồn tại).";
            req.setAttribute("alert", alert);
            req.getRequestDispatcher(Constant.REGISTER).forward(req, resp);
            return;
        }

        // Nếu chọn remember (tuỳ bạn có muốn lưu cookie cho đăng ký)
        if ("on".equals(remember)) {
            Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);
            cookie.setMaxAge(30 * 60); // 30 phút
            String contextPath = req.getContextPath();
            cookie.setPath((contextPath == null || contextPath.isBlank()) ? "/" : contextPath);
            resp.addCookie(cookie);
        }

        // Sau khi tạo xong -> redirect tới login với message (có thể dùng query param hoặc session)
        req.getSession().setAttribute("registerSuccessMsg", "Đăng ký thành công. Vui lòng đăng nhập.");
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
