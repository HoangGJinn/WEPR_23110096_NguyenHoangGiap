<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Đăng nhập</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card shadow-sm p-4">
        <p>Nguyễn Hoàng Giáp - 23110096</p>
          <h2 class="text-center mb-4">Đăng nhập</h2>

          <!-- Form action nên tương ứng với mapping servlet của bạn -->
          <form action="login" method="post">
            <!-- Hiển thị thông báo thành công (nếu có) -->
			<c:if test="${successMessage != null}">
				<div class="alert alert-success alert-dismissible fade show" role="alert" id="successMessage">
					${successMessage}
					<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
				</div>
			</c:if>
			
            <!-- Hiển thị thông báo lỗi (nếu có) -->
			<c:if test="${alert != null}">
				<h3 class="alert alert-danger">${alert}</h3>
			</c:if>
            <div class="mb-3">
              <label for="username" class="form-label">Tài khoản</label>
              <div class="input-group">
                <span class="input-group-text"><i class="fa fa-user"></i></span>
                <input type="text" id="username" name="username" class="form-control" placeholder="Nhập tên tài khoản" required />
              </div>
            </div>

            <div class="mb-3">
              <label for="password" class="form-label">Mật khẩu</label>
              <div class="input-group">
                <span class="input-group-text"><i class="fa fa-lock"></i></span>
                <input type="password" id="password" name="password" class="form-control" placeholder="Nhập mật khẩu" required />
              </div>
            </div>
            <div style="text-align: right;" class="mb-3">
              <a href="${pageContext.request.contextPath}/register">Chưa có tài khoản? Đăng ký ngay</a>
            </div>
            <div style="text-align: right;"  class="mb-3">
              <a href="${pageContext.request.contextPath}/forgotpassword">Quên mật khẩu?</a>
            </div>
            <div class="d-grid">
              <button type="submit" class="btn btn-primary">Đăng nhập</button>
            </div>
          </form>

        </div>
      </div>
    </div>
  </div>

  <!-- Bootstrap JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <!-- Font Awesome (nếu muốn icon) -->
  <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
  
  <script>
    // Auto hide success message sau 5 giây
    const successMessage = document.getElementById('successMessage');
    if (successMessage) {
      setTimeout(() => {
        const bsAlert = new bootstrap.Alert(successMessage);
        bsAlert.close();
      }, 5000);
    }
  </script>
</body>
</html>
