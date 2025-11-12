package giap.hcmute.vn.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import giap.hcmute.vn.model.User;
import giap.hcmute.vn.service.impl.UserServiceImpl;
import giap.hcmute.vn.service.UserService;

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
	ServletException, IOException {
		HttpSession session = req.getSession(false);
		
		if (session != null && session.getAttribute("account") != null) {
		resp.sendRedirect(req.getContextPath()+ "/waiting");
		return;
	
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 resp.setContentType("text/html");
		 resp.setCharacterEncoding("UTF-8");
		 req.setCharacterEncoding("UTF-8");
		String username = req.getParameter("username");
		 String password = req.getParameter("password");
		 boolean isRememberMe = false;
		 String remember = req.getParameter("remember");

		 if("on".equals(remember)){
		 isRememberMe = true;
		 }
		 String alertMsg="";
		 if(username.isEmpty() || password.isEmpty()){
			 alertMsg = "Tài khoản hoặc mật khẩu không được rỗng";
			  req.setAttribute("alert", alertMsg);
			  req.getRequestDispatcher("login.jsp").forward(req, resp);
			  return;
		 }
		UserService service = new UserServiceImpl();
		User user = service.login(username, password);
		if(user!=null){
			  HttpSession session = req.getSession(true);
			  session.setAttribute("account", user);
			  resp.sendRedirect(req.getContextPath()+"/waiting");
			  }else{
			  alertMsg =
			 "Tài khoản hoặc mật khẩu không đúng";
			  req.setAttribute("alert", alertMsg);
			  req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
	}
}