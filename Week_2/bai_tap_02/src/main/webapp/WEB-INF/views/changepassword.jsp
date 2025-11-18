<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Quên mật khẩu</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card shadow-sm p-4">
          <h2 class="text-center mb-4">Đổi mật khẩu</h2>

          <!-- Form action nên tương ứng với mapping servlet của bạn -->
          <form action="changepassword" method="post" id="changePasswordForm">
            <input type="hidden" name="email" value="${email}" />
            
            <c:if test="${alert != null}">
				<div class="alert alert-danger alert-dismissible fade show" role="alert" id="alertMessage">
					${alert}
					<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
				</div>
			</c:if>
			
            <div class="mb-3">
              <label for="newPassword" class="form-label">Mật khẩu mới</label>
              <input type="password" id="newPassword" name="newPassword" class="form-control" placeholder="Nhập mật khẩu mới" required minlength="6" />
              <small class="text-muted">Tối thiểu 6 ký tự</small>
            </div>

            <div class="mb-3">
              <label for="confirmPassword" class="form-label">Xác nhận mật khẩu</label>
              <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" placeholder="Xác nhận mật khẩu mới" required />
              <div class="invalid-feedback">
                Mật khẩu xác nhận không khớp!
              </div>
            </div>

            <div class="d-grid">
              <button type="submit" class="btn btn-primary">Đổi mật khẩu</button>
            </div>
          </form>

        </div>
      </div>
    </div>
  </div>

  <!-- Bootstrap JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  
  <script>
    // Validate mật khẩu khớp nhau
    const form = document.getElementById('changePasswordForm');
    const newPassword = document.getElementById('newPassword');
    const confirmPassword = document.getElementById('confirmPassword');
    
    form.addEventListener('submit', function(event) {
      if (newPassword.value !== confirmPassword.value) {
        event.preventDefault();
        event.stopPropagation();
        confirmPassword.classList.add('is-invalid');
        return false;
      } else {
        confirmPassword.classList.remove('is-invalid');
        confirmPassword.classList.add('is-valid');
      }
    });
    
    // Kiểm tra realtime khi gõ
    confirmPassword.addEventListener('input', function() {
      if (this.value !== newPassword.value) {
        this.classList.add('is-invalid');
        this.classList.remove('is-valid');
      } else {
        this.classList.remove('is-invalid');
        this.classList.add('is-valid');
      }
    });
    
    // Auto hide alert sau 3 giây
    const alertMessage = document.getElementById('alertMessage');
    if (alertMessage) {
      setTimeout(() => {
        const bsAlert = new bootstrap.Alert(alertMessage);
        bsAlert.close();
      }, 3000);
    }
  </script>
</body>
</html>