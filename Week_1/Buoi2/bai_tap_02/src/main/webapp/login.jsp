<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${not empty sessionScope.name}">
	<c:redirect url="/profile.jsp"/>
</c:if>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Login</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/loginStyleSheet.css">
</head>
<body>
  <p class="top-left">
    Xin chào, đây là trang đăng nhập của Nguyen Hoàng Giáp - 23110096
    <br>Hint:
    <br>Username: Hoang_Giap
    <br>Password: ahihi123
  </p>

  <main class="container">
    <h1>Đăng nhập</h1>

    <!-- Hiển thị lỗi: ưu tiên request attribute 'error' (forward), fallback param 'err' (redirect) -->
    <c:if test="${not empty error}">
      <div class="error" style="color:#dc2626; margin-bottom:12px">${error}</div>
    </c:if>
    <c:if test="${empty error and param.err == '1'}">
      <div class="error" style="color:#dc2626; margin-bottom:12px">Tên đăng nhập hoặc mật khẩu không hợp lệ!</div>
    </c:if>

    <form id="loginForm"
          action="${pageContext.request.contextPath}/login"
          method="POST">
      <div class="field">
        <label for="username">Username</label>
        <input id="username" name="username" type="text" placeholder="username" required>
      </div>

      <div class="field">
        <label for="password">Mật khẩu</label>
        <input id="password" name="password" type="password" placeholder="••••••••" minlength="6" required>
      </div>

      <div class="row">
        <label class="checkbox">
          <input type="checkbox" name="remember"> Ghi nhớ đăng nhập
        </label>
        <a class="link" href="#">Quên mật khẩu?</a>
      </div>

      <button class="btn" type="submit">Đăng nhập</button>
    </form>
  </main>
</body>
</html>
