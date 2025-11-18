<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm Category</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body {
            background-color: #f8f9fa;
            padding: 20px;
        }
        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 2px 15px rgba(0,0,0,0.08);
            max-width: 800px;
            margin: 0 auto;
        }
        .card-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 15px 15px 0 0 !important;
            padding: 20px;
        }
        .form-label {
            font-weight: 500;
            color: #333;
        }
        .preview-box {
            border: 2px dashed #dee2e6;
            border-radius: 8px;
            padding: 20px;
            text-align: center;
            background: #f8f9fa;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="card">
            <div class="card-header">
                <h4 class="mb-0"><i class="bi bi-plus-circle"></i> Thêm danh mục mới</h4>
            </div>
            <div class="card-body">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="bi bi-exclamation-triangle"></i> ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                
                <form role="form" action="<c:url value='/admin/category/add'/>" method="post" enctype="multipart/form-data">
                    <div class="mb-4">
                        <label class="form-label">
                            <i class="bi bi-tag"></i> Tên danh mục <span class="text-danger">*</span>
                        </label>
                        <input type="text"
                               class="form-control form-control-lg" 
                               placeholder="Nhập tên danh mục" 
                               name="name" 
                               required />
                    </div>
                    
                    <div class="mb-4">
                        <label class="form-label">
                            <i class="bi bi-image"></i> Ảnh đại diện
                        </label>
                        <input type="file" 
                               class="form-control" 
                               name="icon" 
                               accept="image/*"
                               onchange="previewImage(event)" />
                        <small class="text-muted">Chấp nhận: JPG, PNG, GIF (Tối đa 10MB)</small>
                    </div>
                    
                    <div class="mb-4">
                        <div class="preview-box" id="imagePreview">
                            <i class="bi bi-image" style="font-size: 48px; color: #dee2e6;"></i>
                            <p class="text-muted mb-0">Xem trước ảnh</p>
                        </div>
                    </div>
                    
                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-primary btn-lg">
                            <i class="bi bi-check-circle"></i> Thêm
                        </button>
                        <button type="reset" class="btn btn-secondary btn-lg" onclick="resetPreview()">
                            <i class="bi bi-x-circle"></i> Hủy
                        </button>
                        <a href="<c:url value='/admin/category/list'/>" class="btn btn-outline-secondary btn-lg">
                            <i class="bi bi-arrow-left"></i> Quay lại
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function previewImage(event) {
            const file = event.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    document.getElementById('imagePreview').innerHTML = 
                        '<img src="' + e.target.result + '" class="img-fluid rounded" style="max-height: 200px;">';
                }
                reader.readAsDataURL(file);
            }
        }
        
        function resetPreview() {
            document.getElementById('imagePreview').innerHTML = 
                '<i class="bi bi-image" style="font-size: 48px; color: #dee2e6;"></i>' +
                '<p class="text-muted mb-0">Xem trước ảnh</p>';
        }
    </script>
</body>
</html>