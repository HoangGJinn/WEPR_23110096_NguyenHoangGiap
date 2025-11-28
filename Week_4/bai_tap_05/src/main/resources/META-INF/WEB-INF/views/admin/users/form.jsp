<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="isEdit" value="${user.id != null && user.id > 0}"/>
<sitemesh:write property='title'>${isEdit ? 'Sửa' : 'Thêm'} User - Admin Panel</sitemesh:write>
<sitemesh:write property='head'></sitemesh:write>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="bi bi-people"></i> ${isEdit ? 'Sửa' : 'Thêm'} User</h2>
    <a href="/admin/users" class="btn btn-secondary">
        <i class="bi bi-arrow-left"></i> Quay lại
    </a>
</div>

<div class="card">
    <div class="card-body">
        <form action="/admin/users/${isEdit ? 'edit/' : 'create'}<c:if test="${isEdit}">${user.id}</c:if>" method="post">
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="username" class="form-label">Username</label>
                    <input type="text" class="form-control" id="username" name="username" 
                           value="${user.username}" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" class="form-control" id="password" name="password" 
                           value="${user.password}" ${isEdit ? '' : 'required'}>
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="fullname" class="form-label">Fullname</label>
                    <input type="text" class="form-control" id="fullname" name="fullname" 
                           value="${user.fullname}" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email" 
                           value="${user.email}" required>
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="phone" class="form-label">Phone</label>
                    <input type="text" class="form-control" id="phone" name="phone" 
                           value="${user.phone}">
                </div>
                <div class="col-md-6 mb-3">
                    <label for="images" class="form-label">Images</label>
                    <input type="text" class="form-control" id="images" name="images" 
                           value="${user.images}">
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-6 mb-3">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="admin" name="admin" 
                               value="true" ${user.admin ? 'checked' : ''}>
                        <label class="form-check-label" for="admin">Admin</label>
                    </div>
                </div>
                <div class="col-md-6 mb-3">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="active" name="active" 
                               value="true" ${user.active ? 'checked' : ''}>
                        <label class="form-check-label" for="active">Active</label>
                    </div>
                </div>
            </div>
            
            <div class="d-flex gap-2">
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-save"></i> ${isEdit ? 'Cập nhật' : 'Tạo mới'}
                </button>
                <a href="/admin/users" class="btn btn-secondary">Hủy</a>
            </div>
        </form>
    </div>
</div>

