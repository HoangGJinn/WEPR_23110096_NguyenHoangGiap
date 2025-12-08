// Categories Management Module
let currentCategoryEditId = null;
let categorySearchTimeout;

function initializeCategories() {
    const searchInput = document.getElementById('categorySearch');
    if (searchInput) {
        searchInput.addEventListener('input', function() {
            clearTimeout(categorySearchTimeout);
            categorySearchTimeout = setTimeout(() => {
                searchCategories();
            }, 500);
        });
    }
    
    const categoryForm = document.getElementById('categoryForm');
    if (categoryForm) {
        categoryForm.addEventListener('submit', handleCategorySubmit);
    }
}

async function loadCategories(search = '') {
    try {
        showLoading();
        const response = await CategoryAPI.getAll(search);
        hideLoading();
        
        if (response.success && response.data) {
            const categories = response.data;
            const tbody = document.getElementById('categoriesTableBody');
            
            if (categories.length === 0) {
                tbody.innerHTML = '<tr><td colspan="7" class="text-center text-muted">Không có category nào</td></tr>';
                return;
            }
            
            tbody.innerHTML = categories.map(category => `
                <tr>
                    <td>${category.id || ''}</td>
                    <td>${category.categoryName || ''}</td>
                    <td>${category.categoryCode || ''}</td>
                    <td>
                        ${category.images ? 
                            `<img src="${category.images}" style="max-width: 100px; max-height: 60px;" class="img-thumbnail">` : 
                            '<span class="text-muted">Không có</span>'}
                    </td>
                    <td>${category.user ? (category.user.username || 'N/A') : 'N/A'}</td>
                    <td>
                        <span class="badge ${category.status ? 'bg-success' : 'bg-danger'}">
                            ${category.status ? 'Active' : 'Inactive'}
                        </span>
                    </td>
                    <td>
                        <button class="btn btn-sm btn-warning" onclick="editCategory(${category.id})">
                            <i class="bi bi-pencil"></i> Sửa
                        </button>
                        <button class="btn btn-sm btn-danger" onclick="deleteCategory(${category.id})">
                            <i class="bi bi-trash"></i> Xóa
                        </button>
                    </td>
                </tr>
            `).join('');
        }
    } catch (error) {
        hideLoading();
        console.error('Error loading categories:', error);
        showAlert('Không thể tải danh sách categories: ' + error.message, 'danger');
    }
}

function searchCategories() {
    const search = document.getElementById('categorySearch').value;
    loadCategories(search);
}

async function loadUsersForCategory() {
    try {
        const response = await UserAPI.getAll();
        if (response.success && response.data) {
            const select = document.getElementById('categoryUserId');
            select.innerHTML = '<option value="">-- Chọn User --</option>';
            response.data.forEach(user => {
                const option = document.createElement('option');
                option.value = user.id;
                option.textContent = user.username;
                select.appendChild(option);
            });
        }
    } catch (error) {
        console.error('Error loading users:', error);
    }
}

function openCategoryModal(categoryId = null) {
    currentCategoryEditId = categoryId;
    loadUsersForCategory();
    const modal = new bootstrap.Modal(document.getElementById('categoryModal'));
    const form = document.getElementById('categoryForm');
    
    if (categoryId) {
        document.getElementById('categoryModalTitle').textContent = 'Sửa Category';
        CategoryAPI.getById(categoryId).then(response => {
            if (response.success && response.data) {
                const category = response.data;
                document.getElementById('categoryId').value = category.id;
                document.getElementById('categoryName').value = category.categoryName || '';
                document.getElementById('categoryCode').value = category.categoryCode || '';
                document.getElementById('categoryImages').value = category.images || '';
                document.getElementById('categoryStatus').value = category.status ? 'true' : 'false';
                if (category.user) {
                    document.getElementById('categoryUserId').value = category.user.id;
                }
            }
        });
    } else {
        document.getElementById('categoryModalTitle').textContent = 'Thêm Category mới';
        form.reset();
        document.getElementById('categoryId').value = '';
    }
    
    modal.show();
}

async function handleCategorySubmit(e) {
    e.preventDefault();
    
    const category = {
        categoryName: document.getElementById('categoryName').value,
        categoryCode: document.getElementById('categoryCode').value,
        images: document.getElementById('categoryImages').value || null,
        status: document.getElementById('categoryStatus').value === 'true'
    };
    
    const userId = document.getElementById('categoryUserId').value;
    
    try {
        showLoading();
        let response;
        if (currentCategoryEditId) {
            category.id = currentCategoryEditId;
            response = await CategoryAPI.update(currentCategoryEditId, category);
        } else {
            response = await CategoryAPI.create(category, userId || null);
        }
        hideLoading();
        
        if (response.success) {
            showAlert(currentCategoryEditId ? 'Cập nhật category thành công' : 'Tạo category thành công', 'success');
            bootstrap.Modal.getInstance(document.getElementById('categoryModal')).hide();
            loadCategories();
            if (typeof loadDashboard === 'function') {
                loadDashboard();
            }
        }
    } catch (error) {
        hideLoading();
        console.error('Error saving category:', error);
        showAlert('Lỗi: ' + error.message, 'danger');
    }
}

async function editCategory(id) {
    openCategoryModal(id);
}

async function deleteCategory(id) {
    if (!confirm('Bạn có chắc chắn muốn xóa category này?')) {
        return;
    }
    
    try {
        showLoading();
        const response = await CategoryAPI.delete(id);
        hideLoading();
        
        if (response.success) {
            showAlert('Xóa category thành công', 'success');
            loadCategories();
            if (typeof loadDashboard === 'function') {
                loadDashboard();
            }
        }
    } catch (error) {
        hideLoading();
        console.error('Error deleting category:', error);
        showAlert('Không thể xóa category: ' + error.message, 'danger');
    }
}

