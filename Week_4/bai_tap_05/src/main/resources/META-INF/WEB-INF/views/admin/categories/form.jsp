<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="isEdit" value="${category.id != null && category.id > 0}"/>
<sitemesh:write property='title'>${isEdit ? 'Sửa' : 'Thêm'} Category - Admin Panel</sitemesh:write>
<sitemesh:write property='head'></sitemesh:write>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="bi bi-folder"></i> ${isEdit ? 'Sửa' : 'Thêm'} Category</h2>
    <a href="/admin/categories" class="btn btn-secondary">
        <i class="bi bi-arrow-left"></i> Quay lại
    </a>
</div>

<div class="card">
    <div class="card-body">
        <form action="/admin/categories/${isEdit ? 'edit/' : 'create'}<c:if test="${isEdit}">${category.id}</c:if>" method="post">
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="categoryName" class="form-label">Category Name</label>
                    <input type="text" class="form-control" id="categoryName" name="categoryName" 
                           value="${category.categoryName}" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="categoryCode" class="form-label">Category Code</label>
                    <input type="text" class="form-control" id="categoryCode" name="categoryCode" 
                           value="${category.categoryCode}" required>
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="images" class="form-label">Images</label>
                    <input type="text" class="form-control" id="images" name="images" 
                           value="${category.images}">
                </div>
                <div class="col-md-6 mb-3">
                    <label for="userId" class="form-label">User</label>
                    <select class="form-select" id="userId" name="userId">
                        <option value="">-- Chọn User --</option>
                        <c:forEach var="user" items="${users}">
                            <option value="${user.id}" 
                                    ${category.user != null && category.user.id == user.id ? 'selected' : ''}>
                                ${user.username} (${user.fullname})
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            
            <div class="mb-3">
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="status" name="status" 
                           value="true" ${category.status ? 'checked' : ''}>
                    <label class="form-check-label" for="status">Status (Active)</label>
                </div>
            </div>
            
            <div class="d-flex gap-2">
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-save"></i> ${isEdit ? 'Cập nhật' : 'Tạo mới'}
                </button>
                <a href="/admin/categories" class="btn btn-secondary">Hủy</a>
            </div>
        </form>
    </div>
</div>

