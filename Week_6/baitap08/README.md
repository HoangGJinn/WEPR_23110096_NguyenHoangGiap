# Bài tập 08 - GraphQL API với Thymeleaf

## Thông tin sinh viên
- **Họ tên**: Nguyễn Hoàng Giáp
- **MSSV**: 23110096

## Mô tả bài tập

Xây dựng hệ thống quản lý sản phẩm sử dụng GraphQL API và Thymeleaf với AJAX.

### Yêu cầu
- Database gồm 3 bảng: **Category** (id, name, images), **User** (id, fullname, email, password, phone), **Product** (id, title, quantity, desc, price, userid)
- Quan hệ many-to-many giữa Category và User
- Hiển thị tất cả product có price từ thấp đến cao
- Lấy tất cả product của một category
- CRUD cho User, Product, Category
- Sử dụng GraphQL API và Thymeleaf với AJAX

### GraphQL Mutations
- **Category**: `createCategory`, `updateCategory`, `deleteCategory`
- **User**: `createUser`, `updateUser`, `deleteUser`, `addCategoryToUser`, `removeCategoryFromUser`
- **Product**: `createProduct`, `updateProduct`, `deleteProduct`

### Dữ liệu mẫu
- Tự động khởi tạo dữ liệu mẫu khi ứng dụng chạy lần đầu
- 4 Categories, 4 Users, 10 Products

