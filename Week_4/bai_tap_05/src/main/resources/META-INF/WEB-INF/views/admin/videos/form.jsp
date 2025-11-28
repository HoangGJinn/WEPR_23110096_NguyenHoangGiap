<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="isEdit" value="${video.id != null && video.id > 0}"/>
<sitemesh:write property='title'>${isEdit ? 'Sửa' : 'Thêm'} Video - Admin Panel</sitemesh:write>
<sitemesh:write property='head'></sitemesh:write>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="bi bi-play-circle"></i> ${isEdit ? 'Sửa' : 'Thêm'} Video</h2>
    <a href="/admin/videos" class="btn btn-secondary">
        <i class="bi bi-arrow-left"></i> Quay lại
    </a>
</div>

<div class="card">
    <div class="card-body">
        <form action="/admin/videos/${isEdit ? 'edit/' : 'create'}<c:if test="${isEdit}">${video.id}</c:if>" method="post">
            <div class="mb-3">
                <label for="title" class="form-label">Title</label>
                <input type="text" class="form-control" id="title" name="title" 
                       value="${video.title}" required>
            </div>
            
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="poster" class="form-label">Poster URL</label>
                    <input type="text" class="form-control" id="poster" name="poster" 
                           value="${video.poster}">
                </div>
                <div class="col-md-6 mb-3">
                    <label for="views" class="form-label">Views</label>
                    <input type="text" class="form-control" id="views" name="views" 
                           value="${video.views}">
                </div>
            </div>
            
            <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <textarea class="form-control" id="description" name="description" rows="4">${video.description}</textarea>
            </div>
            
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="categoryId" class="form-label">Category</label>
                    <select class="form-select" id="categoryId" name="categoryId">
                        <option value="">-- Chọn Category --</option>
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.id}" 
                                    ${video.category != null && video.category.id == category.id ? 'selected' : ''}>
                                ${category.categoryName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-6 mb-3">
                    <div class="form-check mt-4">
                        <input class="form-check-input" type="checkbox" id="active" name="active" 
                               value="true" ${video.active ? 'checked' : ''}>
                        <label class="form-check-label" for="active">Active</label>
                    </div>
                </div>
            </div>
            
            <div class="d-flex gap-2">
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-save"></i> ${isEdit ? 'Cập nhật' : 'Tạo mới'}
                </button>
                <a href="/admin/videos" class="btn btn-secondary">Hủy</a>
            </div>
        </form>
    </div>
</div>

