<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<sitemesh:write property='title'>Quản lý Categories - Admin Panel</sitemesh:write>
<sitemesh:write property='head'></sitemesh:write>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="bi bi-folder"></i> Quản lý Categories</h2>
    <a href="/admin/categories/create" class="btn btn-primary">
        <i class="bi bi-plus-circle"></i> Thêm Category mới
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
                    <th>Category Name</th>
                    <th>Category Code</th>
                    <th>User</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="category" items="${categories}">
                    <tr>
                        <td>${category.id}</td>
                        <td>${category.categoryName}</td>
                        <td>${category.categoryCode}</td>
                        <td>${category.user != null ? category.user.username : 'N/A'}</td>
                        <td>
                            <c:if test="${category.status}">
                                <span class="badge bg-success">Active</span>
                            </c:if>
                            <c:if test="${!category.status}">
                                <span class="badge bg-danger">Inactive</span>
                            </c:if>
                        </td>
                        <td>
                            <a href="/admin/categories/edit/${category.id}" class="btn btn-sm btn-warning">
                                <i class="bi bi-pencil"></i> Sửa
                            </a>
                            <a href="/admin/categories/delete/${category.id}" 
                               class="btn btn-sm btn-danger"
                               onclick="return confirm('Bạn có chắc muốn xóa category này?')">
                                <i class="bi bi-trash"></i> Xóa
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

