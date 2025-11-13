<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang chủ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">

            <div class="card shadow-sm p-4">
                <h2 class="text-center mb-4">Thông tin người dùng</h2>

                <!-- Nếu chưa đăng nhập -->
                <c:if test="${sessionScope.account == null}">
                    <div class="alert alert-danger text-center">
                        Bạn chưa đăng nhập! <a href="${pageContext.request.contextPath}/login">Đăng nhập ngay</a>
                    </div>
                </c:if>

                <!-- Nếu đã đăng nhập -->
                <c:if test="${sessionScope.account != null}">
                    <table class="table table-bordered">
                        <tr>
                            <th>ID</th>
                            <td>${sessionScope.account.id}</td>
                        </tr>

                        <tr>
                            <th>Username</th>
                            <td>${sessionScope.account.username}</td>
                        </tr>

                        <tr>
                            <th>Email</th>
                            <td>${sessionScope.account.email}</td>
                        </tr>

                        <tr>
                            <th>Họ và tên</th>
                            <td>${sessionScope.account.fullname}</td>
                        </tr>

                        <tr>
                            <th>Số điện thoại</th>
                            <td>${sessionScope.account.phone}</td>
                        </tr>

                        <tr>
                            <th>Ảnh đại diện</th>
                            <td>
                                <c:if test="${not empty sessionScope.account.avatar}">
                                    <img src="${sessionScope.account.avatar}" width="120" class="img-thumbnail" />
                                </c:if>
                                <c:if test="${empty sessionScope.account.avatar}">
                                    (Chưa có avatar)
                                </c:if>
                            </td>
                        </tr>

                        <tr>
                            <th>Ngày tạo</th>
                            <td>${sessionScope.account.createdDate}</td>
                        </tr>
                    </table>

                    <div class="text-center mt-3">
                        <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger">
                            Đăng xuất
                        </a>
                    </div>
                </c:if>

            </div>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
