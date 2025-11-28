<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<sitemesh:write property='title'>Quản lý Users - Admin Panel</sitemesh:write>
<sitemesh:write property='head'></sitemesh:write>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="bi bi-people"></i> Quản lý Users</h2>
    <a href="/admin/users/create" class="btn btn-primary">
        <i class="bi bi-plus-circle"></i> Thêm User mới
    </a>
</div>

<c:if test="${not empty success}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        ${success}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
</c:if>

<c:if test="${not empty error}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        ${error}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
</c:if>

<div class="card">
    <div class="card-body">
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Fullname</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Admin</th>
                    <th>Active</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.fullname}</td>
                        <td>${user.email}</td>
                        <td>${user.phone}</td>
                        <td>
                            <c:if test="${user.admin}">
                                <span class="badge bg-success">Admin</span>
                            </c:if>
                            <c:if test="${!user.admin}">
                                <span class="badge bg-secondary">User</span>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${user.active}">
                                <span class="badge bg-success">Active</span>
                            </c:if>
                            <c:if test="${!user.active}">
                                <span class="badge bg-danger">Inactive</span>
                            </c:if>
                        </td>
                        <td>
                            <a href="/admin/users/edit/${user.id}" class="btn btn-sm btn-warning">
                                <i class="bi bi-pencil"></i> Sửa
                            </a>
                            <a href="/admin/users/delete/${user.id}" 
                               class="btn btn-sm btn-danger"
                               onclick="return confirm('Bạn có chắc muốn xóa user này?')">
                                <i class="bi bi-trash"></i> Xóa
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

