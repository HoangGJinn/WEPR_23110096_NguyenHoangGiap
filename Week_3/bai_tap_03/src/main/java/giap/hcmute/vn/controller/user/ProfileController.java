package giap.hcmute.vn.controller.user;

import java.io.IOException;

import giap.hcmute.vn.dto.UserDTO;
import giap.hcmute.vn.constant.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/profile")
public class ProfileController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDTO currentUser = (UserDTO) session.getAttribute(Constant.SESSION_ACCOUNT);

        if (currentUser == null){
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.setAttribute(Constant.SESSION_ACCOUNT, currentUser);
        req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }
}
