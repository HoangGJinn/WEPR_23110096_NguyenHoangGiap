<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>User info</title>
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

            <div class="card shadow-sm p-4">
                <h2 class="text-center mb-4">Thông tin người dùng</h2>

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
                </c:if>
                <c:if test="${sessionScope.account == null}">
                    <p class="text-center">Bạn chưa đăng nhập. Vui lòng <a href="${pageContext.request.contextPath}/login">đăng nhập</a> để xem thông tin cá nhân.</p>
                </c:if>
            </div>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
