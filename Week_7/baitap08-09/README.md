# Bài tập 09 - Xây dựng từ bài tập 08, phát triển thêm validate cho các form login, register, tạo sản phẩm,..
# Phân quyền ADMIN và USER

## Thông tin sinh viên
- **Họ tên**: Nguyễn Hoàng Giáp
- **MSSV**: 23110096

## Mô tả bài tập

Xây dựng hệ thống quản lý sản phẩm sử dụng GraphQL API và Thymeleaf với AJAX.
validate các form, phân quyền ADMIN và USER. User chỉ có thể vào trang home không thể vào trang quản lý sản phẩm, category.

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

