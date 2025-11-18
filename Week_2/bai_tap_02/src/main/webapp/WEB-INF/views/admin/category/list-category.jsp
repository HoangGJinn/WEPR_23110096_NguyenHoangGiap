<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách Category</title>
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
        }
        .card-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 15px 15px 0 0 !important;
            padding: 20px;
        }
        .table img {
            border-radius: 8px;
            object-fit: cover;
        }
        .btn-action {
            padding: 5px 15px;
            border-radius: 6px;
            transition: all 0.3s;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="card">
            <div class="card-header">
                <div class="d-flex justify-content-between align-items-center">
                    <h4 class="mb-0"><i class="bi bi-grid"></i> Danh sách Category</h4>
                    <a href="<c:url value='/admin/category/add'/>" class="btn btn-light">
                        <i class="bi bi-plus-circle"></i> Thêm mới
                    </a>
                </div>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover table-bordered align-middle">
                        <thead class="table-light">
                            <tr>
                                <th width="5%" class="text-center">STT</th>
                                <th width="20%" class="text-center">Hình ảnh</th>
                                <th width="35%">Tên danh mục</th>
                                <th width="20%" class="text-center">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${cateList}" var="cate" varStatus="STT">
                                <tr>
                                    <td class="text-center">${STT.index + 1}</td>
                                    <td class="text-center">
                                        <c:url value="/image?fname=${cate.icon}" var="imgUrl"></c:url>
                                        <img height="100" width="150" src="${imgUrl}" alt="${cate.catename}" class="img-thumbnail" />
                                    </td>
                                    <td>
                                        <strong>${cate.catename}</strong>
                                    </td>
                                    <td class="text-center">
                                        <a href="<c:url value='/admin/category/edit?id=${cate.cateid}'/>" 
                                           class="btn btn-sm btn-primary btn-action">
                                            <i class="bi bi-pencil-square"></i> Sửa
                                        </a>
                                        <a href="<c:url value='/admin/category/delete?id=${cate.cateid}'/>" 
                                           class="btn btn-sm btn-danger btn-action"
                                           onclick="return confirm('Bạn có chắc muốn xóa category này?')">
                                            <i class="bi bi-trash"></i> Xóa
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty cateList}">
                                <tr>
                                    <td colspan="4" class="text-center text-muted">
                                        <i class="bi bi-inbox"></i> Chưa có category nào
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="card-footer text-muted">
                <a href="<c:url value='/admin/home'/>" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left"></i> Quay lại Dashboard
                </a>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>