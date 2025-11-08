<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String username = (String)session.getAttribute("name");

	if (username == null){
		response.sendRedirect(request.getContextPath() + "/login.jsp");
		return;
	}
	
    // (Khuyên dùng) Chống back-cache sau khi logout
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profile user</title>
</head>
<body>
	<h2>Xin chào, <%= username %></h2>
	
	<p><a href="<%= request.getContextPath() %>/logout">Đăng xuất</a></p>
</body>
</html>