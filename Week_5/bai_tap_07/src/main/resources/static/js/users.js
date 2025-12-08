// Users Management Module
let currentUserEditId = null;
let userSearchTimeout;

function initializeUsers() {
    const searchInput = document.getElementById('userSearch');
    if (searchInput) {
        searchInput.addEventListener('input', function() {
            clearTimeout(userSearchTimeout);
            userSearchTimeout = setTimeout(() => {
                searchUsers();
            }, 500);
        });
    }
    
    const userForm = document.getElementById('userForm');
    if (userForm) {
        userForm.addEventListener('submit', handleUserSubmit);
    }
}

async function loadUsers(search = '') {
    try {
        showLoading();
        const response = await UserAPI.getAll(search);
        hideLoading();
        
        if (response.success && response.data) {
            const users = response.data;
            const tbody = document.getElementById('usersTableBody');
            
            if (users.length === 0) {
                tbody.innerHTML = '<tr><td colspan="8" class="text-center text-muted">Không có user nào</td></tr>';
                return;
            }
            
            tbody.innerHTML = users.map(user => `
                <tr>
                    <td>${user.id || ''}</td>
                    <td>${user.username || ''}</td>
                    <td>${user.fullname || ''}</td>
                    <td>${user.email || ''}</td>
                    <td>${user.phone || ''}</td>
                    <td>
                        <span class="badge ${user.admin ? 'bg-success' : 'bg-secondary'}">
                            ${user.admin ? 'Admin' : 'User'}
                        </span>
                    </td>
                    <td>
                        <span class="badge ${user.active ? 'bg-success' : 'bg-danger'}">
                            ${user.active ? 'Active' : 'Inactive'}
                        </span>
                    </td>
                    <td>
                        <button class="btn btn-sm btn-warning" onclick="editUser(${user.id})">
                            <i class="bi bi-pencil"></i> Sửa
                        </button>
                        <button class="btn btn-sm btn-danger" onclick="deleteUser(${user.id})">
                            <i class="bi bi-trash"></i> Xóa
                        </button>
                    </td>
                </tr>
            `).join('');
        }
    } catch (error) {
        hideLoading();
        console.error('Error loading users:', error);
        showAlert('Không thể tải danh sách users: ' + error.message, 'danger');
    }
}

function searchUsers() {
    const search = document.getElementById('userSearch').value;
    loadUsers(search);
}

function openUserModal(userId = null) {
    currentUserEditId = userId;
    const modal = new bootstrap.Modal(document.getElementById('userModal'));
    const form = document.getElementById('userForm');
    
    if (userId) {
        document.getElementById('userModalTitle').textContent = 'Sửa User';
        UserAPI.getById(userId).then(response => {
            if (response.success && response.data) {
                const user = response.data;
                document.getElementById('userId').value = user.id;
                document.getElementById('userUsername').value = user.username || '';
                document.getElementById('userFullname').value = user.fullname || '';
                document.getElementById('userEmail').value = user.email || '';
                document.getElementById('userPhone').value = user.phone || '';
                document.getElementById('userImages').value = user.images || '';
                document.getElementById('userAdmin').value = user.admin ? 'true' : 'false';
                document.getElementById('userActive').value = user.active ? 'true' : 'false';
            }
        });
    } else {
        document.getElementById('userModalTitle').textContent = 'Thêm User mới';
        form.reset();
        document.getElementById('userId').value = '';
    }
    
    modal.show();
}

async function handleUserSubmit(e) {
    e.preventDefault();
    
    const user = {
        username: document.getElementById('userUsername').value,
        password: document.getElementById('userPassword').value,
        fullname: document.getElementById('userFullname').value || null,
        email: document.getElementById('userEmail').value || null,
        phone: document.getElementById('userPhone').value || null,
        images: document.getElementById('userImages').value || null,
        admin: document.getElementById('userAdmin').value === 'true',
        active: document.getElementById('userActive').value === 'true'
    };
    
    try {
        showLoading();
        let response;
        if (currentUserEditId) {
            user.id = currentUserEditId;
            response = await UserAPI.update(currentUserEditId, user);
        } else {
            response = await UserAPI.create(user);
        }
        hideLoading();
        
        if (response.success) {
            showAlert(currentUserEditId ? 'Cập nhật user thành công' : 'Tạo user thành công', 'success');
            bootstrap.Modal.getInstance(document.getElementById('userModal')).hide();
            loadUsers();
            if (typeof loadDashboard === 'function') {
                loadDashboard();
            }
        }
    } catch (error) {
        hideLoading();
        console.error('Error saving user:', error);
        showAlert('Lỗi: ' + error.message, 'danger');
    }
}

async function editUser(id) {
    openUserModal(id);
}

async function deleteUser(id) {
    if (!confirm('Bạn có chắc chắn muốn xóa user này?')) {
        return;
    }
    
    try {
        showLoading();
        const response = await UserAPI.delete(id);
        hideLoading();
        
        if (response.success) {
            showAlert('Xóa user thành công', 'success');
            loadUsers();
            if (typeof loadDashboard === 'function') {
                loadDashboard();
            }
        }
    } catch (error) {
        hideLoading();
        console.error('Error deleting user:', error);
        showAlert('Không thể xóa user: ' + error.message, 'danger');
    }
}

