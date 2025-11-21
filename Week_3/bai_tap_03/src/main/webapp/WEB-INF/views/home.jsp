<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang chủ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .topbar {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 10px 0;
            color: white;
        }
        .topbar ul {
            margin: 0;
            padding: 0;
            text-align: right;
        }
        .topbar ul li {
            display: inline-block;
            margin-left: 15px;
        }
        .topbar a {
            color: white;
            text-decoration: none;
            transition: opacity 0.3s;
        }
        .topbar a:hover {
            opacity: 0.8;
        }
    </style>
</head>

<body class="bg-light">

<!-- Include topbar -->
<%@ include file="/WEB-INF/views/common/topbar.jsp" %>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            
            <!-- Error Message -->
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="fas fa-exclamation-circle"></i> ${errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>
            
            <!-- Success Message -->
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="fas fa-check-circle"></i> ${successMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <div class="card shadow-sm p-4">
                <h2 class="mb-4">Danh sách Category</h2>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th width="150">Hình ảnh</th>
                            <th>Tên danh mục</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="category" items="${categories}">
                            <tr>
                                <td>
                                    <c:if test="${not empty category.icon}">
                                        <c:url value="/image?fname=${category.icon}" var="imgUrl"></c:url>
                                        <img src="${imgUrl}" alt="${category.catename}" class="img-thumbnail" style="max-width: 120px; max-height: 80px;" />
                                    </c:if>
                                    <c:if test="${empty category.icon}">
                                        <span class="text-muted">Không có ảnh</span>
                                    </c:if>
                                </td>
                                <td>
                                    <h5 class="mb-0">${category.catename}</h5>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
