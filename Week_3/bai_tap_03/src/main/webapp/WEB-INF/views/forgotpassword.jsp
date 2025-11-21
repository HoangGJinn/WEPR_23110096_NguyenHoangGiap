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
          <h2 class="text-center mb-4">Quên mật khẩu</h2>

          <!-- Form action nên tương ứng với mapping servlet của bạn -->
          <form action="forgotpassword" method="post">
            <!-- Hiển thị thông báo lỗi (nếu có) -->
			<c:if test="${alert != null}">
				<h3 class="alert alert-danger alert-dismissible fade show" role="alert" id="alertMessage">
					${alert}
					<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
				</h3>
			</c:if>
            <div class="mb-3">
              <label for="email" class="form-label">Email</label>
              <div class="input-group">
                <span class="input-group-text"><i class="fa fa-envelope"></i></span>
                <input type="email" id="email" name="email" class="form-control" placeholder="Nhập email của bạn" required />
              </div>
            </div>

            <div class="d-grid">
              <button type="submit" class="btn btn-primary">Gửi yêu cầu</button>
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
    // Tự động ẩn alert sau 3 giây
    const alertMessage = document.getElementById('alertMessage');
    if (alertMessage) {
      setTimeout(() => {
        const bsAlert = new bootstrap.Alert(alertMessage);
        bsAlert.close();
      }, 3000); // 3000ms = 3 giây
    }
  </script>
</body>
</html>