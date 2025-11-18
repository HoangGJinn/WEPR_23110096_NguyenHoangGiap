<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>

<div class="container mt-5" style="max-width:480px;">
  <div class="card shadow-sm">
    <div class="card-body">
      <h3 class="card-title text-center mb-4">Tạo tài khoản mới</h3>

      <c:if test="${not empty alert}">
        <div class="alert alert-danger" role="alert">${alert}</div>
      </c:if>

      <form action="register" method="post" novalidate>
        <div class="mb-3">
          <label class="form-label">Tài khoản</label>
          <input type="text" name="username" class="form-control" placeholder="Nhập tài khoản" required />
        </div>

        <div class="mb-3">
          <label class="form-label">Email</label>
          <input type="email" name="email" class="form-control" placeholder="you@example.com" required />
        </div>

        <div class="mb-3">
          <label class="form-label">Mật khẩu</label>
          <input type="password" name="password" class="form-control" placeholder="Mật khẩu" required />
        </div>

        <div class="mb-3">
          <label class="form-label">Xác nhận mật khẩu</label>
          <input type="password" name="confirmPassword" class="form-control" placeholder="Nhập lại mật khẩu" required />
        </div>

        <div class="d-grid">
          <button type="submit" class="btn btn-primary">Đăng ký</button>
        </div>
      </form>

      <p class="text-center mt-3 small">
        Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
      </p>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
