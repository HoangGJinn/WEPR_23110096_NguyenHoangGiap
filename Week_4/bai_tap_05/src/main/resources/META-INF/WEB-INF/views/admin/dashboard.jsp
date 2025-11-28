<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<sitemesh:write property='title'>Dashboard - Admin Panel</sitemesh:write>
<sitemesh:write property='head'></sitemesh:write>

<div class="row mb-4">
    <div class="col-12">
        <h2 class="mb-4">
            <i class="bi bi-speedometer2"></i> Dashboard
        </h2>
    </div>
</div>

<div class="row">
    <div class="col-md-4 mb-4">
        <div class="card text-white bg-primary">
            <div class="card-body">
                <h5 class="card-title">
                    <i class="bi bi-people"></i> Users
                </h5>
                <p class="card-text">Quản lý người dùng</p>
                <a href="/admin/users" class="btn btn-light">Xem chi tiết</a>
            </div>
        </div>
    </div>
    
    <div class="col-md-4 mb-4">
        <div class="card text-white bg-success">
            <div class="card-body">
                <h5 class="card-title">
                    <i class="bi bi-folder"></i> Categories
                </h5>
                <p class="card-text">Quản lý danh mục</p>
                <a href="/admin/categories" class="btn btn-light">Xem chi tiết</a>
            </div>
        </div>
    </div>
    
    <div class="col-md-4 mb-4">
        <div class="card text-white bg-info">
            <div class="card-body">
                <h5 class="card-title">
                    <i class="bi bi-play-circle"></i> Videos
                </h5>
                <p class="card-text">Quản lý video</p>
                <a href="/admin/videos" class="btn btn-light">Xem chi tiết</a>
            </div>
        </div>
    </div>
</div>

