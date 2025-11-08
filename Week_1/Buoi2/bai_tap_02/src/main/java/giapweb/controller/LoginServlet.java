package giapweb.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if (username.equals("Hoang_Giap") && password.equals("ahihi123")) {
			session.setAttribute("name", username);
			
		    request.changeSessionId();                 // chống session fixation
		    session.setMaxInactiveInterval(15 * 60); 
			response.sendRedirect(request.getContextPath() + "/profile.jsp");
		} else {
			request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không hợp lệ!");
			request.getRequestDispatcher("login.jsp").include(request,
					response);
		}
	}
}
