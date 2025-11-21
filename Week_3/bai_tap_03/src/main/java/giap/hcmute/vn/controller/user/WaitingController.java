package giap.hcmute.vn.controller.user;

import java.io.IOException;

import giap.hcmute.vn.dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/waiting")
public class WaitingController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        if (session != null && session.getAttribute("account") != null) {
            UserDTO u = (UserDTO) session.getAttribute("account");
            req.setAttribute("username", u.getUsername());

            if (u.getRoleid() == 1) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else if (u.getRoleid() == 2) {
                resp.sendRedirect(req.getContextPath() + "/manager/dashboard");
            } else {
                resp.sendRedirect(req.getContextPath() + "/home");
            }

        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
