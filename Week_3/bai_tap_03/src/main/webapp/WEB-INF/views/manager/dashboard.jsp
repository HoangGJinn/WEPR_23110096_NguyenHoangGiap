<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manager Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body {
            background-color: #f8f9fa;
            padding: 20px;
        }
        .dashboard-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            border-radius: 15px;
            margin-bottom: 30px;
        }
        .stat-card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 2px 15px rgba(0,0,0,0.08);
            padding: 25px;
            margin-bottom: 20px;
            transition: transform 0.3s;
        }
        .stat-card:hover {
            transform: translateY(-5px);
        }
        .stat-icon {
            font-size: 3rem;
            opacity: 0.8;
        }
        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 2px 15px rgba(0,0,0,0.08);
        }
        .table img {
            border-radius: 8px;
            object-fit: cover;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Header -->
        <div class="dashboard-header">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h2 class="mb-2">
                        <i class="bi bi-speedometer2"></i> Manager Dashboard
                    </h2>
                    <p class="mb-0">
                        Xin chào, <strong>${sessionScope.account.fullname}</strong>!
                    </p>
                </div>
                <div>
                    <a href="<c:url value='/logout'/>" class="btn btn-light">
                        <i class="bi bi-box-arrow-right"></i> Đăng xuất
                    </a>
                </div>
            </div>
        </div>
        
        <!-- Statistics -->
        <div class="row mb-4">
            <div class="col-md-4">
                <div class="stat-card bg-primary text-white">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="text-uppercase mb-2">Tổng Category</h6>
                            <h2 class="mb-0">${categoryCount}</h2>
                        </div>
                        <div class="stat-icon">
                            <i class="bi bi-grid"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Categories Table -->
        <div class="card">
            <div class="card-header bg-white">
                <div class="d-flex justify-content-between align-items-center">
                    <h5 class="mb-0"><i class="bi bi-grid"></i> Category của tôi</h5>
                    <a href="<c:url value='/manager/category/list'/>" class="btn btn-primary">
                        <i class="bi bi-list"></i> Quản lý Category
                    </a>
                </div>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-light">
                            <tr>
                                <th width="10%" class="text-center">STT</th>
                                <th width="20%" class="text-center">Hình ảnh</th>
                                <th width="40%">Tên danh mục</th>
                                <th width="30%" class="text-center">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${categories}" var="cate" varStatus="STT">
                                <tr>
                                    <td class="text-center">${STT.index + 1}</td>
                                    <td class="text-center">
                                        <c:if test="${not empty cate.icon}">
                                            <c:url value="/image?fname=${cate.icon}" var="imgUrl"></c:url>
                                            <img src="${imgUrl}" alt="${cate.catename}" width="80" height="60" class="img-thumbnail" />
                                        </c:if>
                                        <c:if test="${empty cate.icon}">
                                            <span class="text-muted">Không có ảnh</span>
                                        </c:if>
                                    </td>
                                    <td>
                                        <strong>${cate.catename}</strong>
                                    </td>
                                    <td class="text-center">
                                        <a href="<c:url value='/manager/category/edit?id=${cate.cateid}'/>" 
                                           class="btn btn-sm btn-primary">
                                            <i class="bi bi-pencil"></i> Sửa
                                        </a>
                                        <a href="<c:url value='/manager/category/delete?id=${cate.cateid}'/>" 
                                           class="btn btn-sm btn-danger"
                                           onclick="return confirm('Bạn có chắc muốn xóa category này?')">
                                            <i class="bi bi-trash"></i> Xóa
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty categories}">
                                <tr>
                                    <td colspan="4" class="text-center text-muted py-4">
                                        <i class="bi bi-inbox" style="font-size: 2rem;"></i>
                                        <p class="mt-2">Bạn chưa có category nào</p>
                                        <a href="<c:url value='/manager/category/add'/>" class="btn btn-primary">
                                            <i class="bi bi-plus-circle"></i> Thêm mới ngay
                                        </a>
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
