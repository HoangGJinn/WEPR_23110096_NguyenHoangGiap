<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        :root {
            --sidebar-width: 250px;
        }

        body {
            min-height: 100vh;
        }

        .sidebar {
            position: fixed;
            top: 0;
            left: 0;
            height: 100vh;
            width: var(--sidebar-width);
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 20px 0;
            box-shadow: 2px 0 10px rgba(0,0,0,0.1);
        }

        .sidebar .logo {
            padding: 20px;
            color: white;
            font-size: 24px;
            font-weight: bold;
            text-align: center;
            border-bottom: 1px solid rgba(255,255,255,0.1);
            margin-bottom: 20px;
        }

        .sidebar .nav-link {
            color: rgba(255,255,255,0.8);
            padding: 12px 25px;
            margin: 5px 15px;
            border-radius: 8px;
            transition: all 0.3s;
            display: flex;
            align-items: center;
        }

        .sidebar .nav-link:hover,
        .sidebar .nav-link.active {
            background: rgba(255,255,255,0.2);
            color: white;
            transform: translateX(5px);
        }

        .sidebar .nav-link i {
            margin-right: 10px;
            font-size: 18px;
        }

        .main-content {
            margin-left: var(--sidebar-width);
            padding: 20px;
        }

        .top-bar {
            background: white;
            padding: 15px 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            margin-bottom: 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .welcome-text {
            font-size: 24px;
            font-weight: 600;
            color: #333;
        }

        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 2px 15px rgba(0,0,0,0.08);
            transition: transform 0.3s;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 20px rgba(0,0,0,0.12);
        }

        .card-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 15px 15px 0 0 !important;
            padding: 20px;
        }

        .icon-box {
            width: 60px;
            height: 60px;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 28px;
            margin-bottom: 15px;
        }

        .icon-box.primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .icon-box.success {
            background: linear-gradient(135deg, #84fab0 0%, #8fd3f4 100%);
            color: white;
        }

        .icon-box.warning {
            background: linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%);
            color: #ff6b6b;
        }

        .icon-box.info {
            background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%);
            color: #6c5ce7;
        }

        .stat-number {
            font-size: 32px;
            font-weight: bold;
            color: #333;
        }

        .logout-btn {
            background: rgba(255,255,255,0.2);
            border: none;
            color: white;
            padding: 8px 20px;
            border-radius: 8px;
            transition: all 0.3s;
        }

        .logout-btn:hover {
            background: rgba(255,255,255,0.3);
        }
    </style>
</head>
<body>
    <!-- Sidebar -->
    <div class="sidebar">
        <div class="logo">
            <i class="bi bi-speedometer2"></i> Admin Panel
        </div>
        <nav class="nav flex-column">
            <a class="nav-link active" href="<c:url value='/admin/home'/>">
                <i class="bi bi-house-door"></i> Dashboard
            </a>
            <a class="nav-link" href="<c:url value='/admin/category/list'/>">
                <i class="bi bi-grid"></i> Quản lý Category
            </a>
            <a class="nav-link" href="<c:url value='/admin/category/add'/>">
                <i class="bi bi-plus-circle"></i> Thêm Category
            </a>
            <a class="nav-link" href="<c:url value='/admin/users'/>">
                <i class="bi bi-people"></i> Quản lý Users
            </a>
            <a class="nav-link" href="<c:url value='/admin/settings'/>">
                <i class="bi bi-gear"></i> Cài đặt
            </a>
        </nav>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <!-- Top Bar -->
        <div class="top-bar">
            <div class="welcome-text">
                <i class="bi bi-emoji-smile"></i> Xin chào, ${sessionScope.account.fullname}!
            </div>
            <div>
                <a href="<c:url value='/logout'/>" class="btn logout-btn">
                    <i class="bi bi-box-arrow-right"></i> Đăng xuất
                </a>
            </div>
        </div>

        <!-- Dashboard Cards -->
        <div class="row">
            <div class="col-md-12 mb-4">
                <h2 class="mb-4"><i class="bi bi-bar-chart"></i> Tổng quan hệ thống</h2>
            </div>

            <!-- Statistics Cards -->
            <div class="col-md-3 mb-4">
                <div class="card">
                    <div class="card-body text-center">
                        <div class="icon-box primary mx-auto">
                            <i class="bi bi-grid"></i>
                        </div>
                        <div class="stat-number">24</div>
                        <p class="text-muted mb-0">Tổng Categories</p>
                    </div>
                </div>
            </div>

            <div class="col-md-3 mb-4">
                <div class="card">
                    <div class="card-body text-center">
                        <div class="icon-box success mx-auto">
                            <i class="bi bi-people"></i>
                        </div>
                        <div class="stat-number">152</div>
                        <p class="text-muted mb-0">Người dùng</p>
                    </div>
                </div>
            </div>

            <div class="col-md-3 mb-4">
                <div class="card">
                    <div class="card-body text-center">
                        <div class="icon-box warning mx-auto">
                            <i class="bi bi-cart"></i>
                        </div>
                        <div class="stat-number">89</div>
                        <p class="text-muted mb-0">Đơn hàng</p>
                    </div>
                </div>
            </div>

            <div class="col-md-3 mb-4">
                <div class="card">
                    <div class="card-body text-center">
                        <div class="icon-box info mx-auto">
                            <i class="bi bi-graph-up"></i>
                        </div>
                        <div class="stat-number">+12%</div>
                        <p class="text-muted mb-0">Tăng trưởng</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Quick Actions -->
        <div class="row mt-4">
            <div class="col-md-12 mb-3">
                <h2 class="mb-4"><i class="bi bi-lightning"></i> Thao tác nhanh</h2>
            </div>

            <div class="col-md-6 mb-4">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0"><i class="bi bi-grid"></i> Quản lý Category</h5>
                    </div>
                    <div class="card-body">
                        <p class="text-muted">Quản lý danh mục sản phẩm, thêm, sửa, xóa category</p>
                        <div class="d-flex gap-2">
                            <a href="<c:url value='/admin/category/list'/>" class="btn btn-primary">
                                <i class="bi bi-list-ul"></i> Xem danh sách
                            </a>
                            <a href="<c:url value='/admin/category/add'/>" class="btn btn-success">
                                <i class="bi bi-plus-circle"></i> Thêm mới
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 mb-4">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0"><i class="bi bi-people"></i> Quản lý Users</h5>
                    </div>
                    <div class="card-body">
                        <p class="text-muted">Quản lý người dùng, phân quyền và theo dõi hoạt động</p>
                        <div class="d-flex gap-2">
                            <a href="<c:url value='/admin/users'/>" class="btn btn-primary">
                                <i class="bi bi-list-ul"></i> Xem danh sách
                            </a>
                            <a href="<c:url value='/admin/users/add'/>" class="btn btn-success">
                                <i class="bi bi-plus-circle"></i> Thêm mới
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Recent Activities -->
        <div class="row mt-4">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0"><i class="bi bi-clock-history"></i> Hoạt động gần đây</h5>
                    </div>
                    <div class="card-body">
                        <div class="list-group list-group-flush">
                            <div class="list-group-item d-flex justify-content-between align-items-center">
                                <div>
                                    <i class="bi bi-plus-circle text-success"></i>
                                    <strong>Category mới</strong> "Điện thoại" đã được thêm
                                </div>
                                <small class="text-muted">5 phút trước</small>
                            </div>
                            <div class="list-group-item d-flex justify-content-between align-items-center">
                                <div>
                                    <i class="bi bi-pencil text-primary"></i>
                                    <strong>Category</strong> "Laptop" đã được cập nhật
                                </div>
                                <small class="text-muted">1 giờ trước</small>
                            </div>
                            <div class="list-group-item d-flex justify-content-between align-items-center">
                                <div>
                                    <i class="bi bi-person-plus text-info"></i>
                                    <strong>User mới</strong> "nguyenvana" đã đăng ký
                                </div>
                                <small class="text-muted">2 giờ trước</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
