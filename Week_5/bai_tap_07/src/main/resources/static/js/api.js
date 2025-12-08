// API Utility Functions
const API_BASE_URL = '/api';

// Helper function to handle API responses
async function handleApiResponse(response) {
    const data = await response.json();
    if (!response.ok) {
        throw new Error(data.message || 'Có lỗi xảy ra');
    }
    return data;
}

// User API
const UserAPI = {
    getAll: async (search = '') => {
        const url = search ? `${API_BASE_URL}/users?search=${encodeURIComponent(search)}` : `${API_BASE_URL}/users`;
        const response = await fetch(url);
        return handleApiResponse(response);
    },
    
    getById: async (id) => {
        const response = await fetch(`${API_BASE_URL}/users/${id}`);
        return handleApiResponse(response);
    },
    
    create: async (user) => {
        const response = await fetch(`${API_BASE_URL}/users`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(user)
        });
        return handleApiResponse(response);
    },
    
    update: async (id, user) => {
        const response = await fetch(`${API_BASE_URL}/users/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(user)
        });
        return handleApiResponse(response);
    },
    
    delete: async (id) => {
        const response = await fetch(`${API_BASE_URL}/users/${id}`, {
            method: 'DELETE'
        });
        return handleApiResponse(response);
    }
};

// Category API
const CategoryAPI = {
    getAll: async (search = '') => {
        const url = search ? `${API_BASE_URL}/categories?search=${encodeURIComponent(search)}` : `${API_BASE_URL}/categories`;
        const response = await fetch(url);
        return handleApiResponse(response);
    },
    
    getById: async (id) => {
        const response = await fetch(`${API_BASE_URL}/categories/${id}`);
        return handleApiResponse(response);
    },
    
    create: async (category, userId = null) => {
        let url = `${API_BASE_URL}/categories`;
        if (userId) {
            url += `?userId=${userId}`;
        }
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(category)
        });
        return handleApiResponse(response);
    },
    
    update: async (id, category) => {
        const response = await fetch(`${API_BASE_URL}/categories/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(category)
        });
        return handleApiResponse(response);
    },
    
    delete: async (id) => {
        const response = await fetch(`${API_BASE_URL}/categories/${id}`, {
            method: 'DELETE'
        });
        return handleApiResponse(response);
    }
};

// Video API
const VideoAPI = {
    getAll: async (search = '') => {
        const url = search ? `${API_BASE_URL}/videos?search=${encodeURIComponent(search)}` : `${API_BASE_URL}/videos`;
        const response = await fetch(url);
        return handleApiResponse(response);
    },
    
    getById: async (id) => {
        const response = await fetch(`${API_BASE_URL}/videos/${id}`);
        return handleApiResponse(response);
    },
    
    getByCategory: async (categoryId) => {
        const response = await fetch(`${API_BASE_URL}/videos/category/${categoryId}`);
        return handleApiResponse(response);
    },
    
    create: async (video, categoryId = null) => {
        let url = `${API_BASE_URL}/videos`;
        if (categoryId) {
            url += `?categoryId=${categoryId}`;
        }
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(video)
        });
        return handleApiResponse(response);
    },
    
    update: async (id, video) => {
        const response = await fetch(`${API_BASE_URL}/videos/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(video)
        });
        return handleApiResponse(response);
    },
    
    delete: async (id) => {
        const response = await fetch(`${API_BASE_URL}/videos/${id}`, {
            method: 'DELETE'
        });
        return handleApiResponse(response);
    }
};

// Admin API
const AdminAPI = {
    getDashboard: async () => {
        const response = await fetch(`${API_BASE_URL}/admin/dashboard`);
        return handleApiResponse(response);
    }
};

// Utility function to show toast/alert
function showAlert(message, type = 'success') {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show shadow-sm border-0`;
    alertDiv.setAttribute('role', 'alert');
    alertDiv.innerHTML = `
        <i class="bi bi-${type === 'success' ? 'check-circle' : 'exclamation-triangle'}-fill me-2"></i>
        <span>${message}</span>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    const container = document.querySelector('.main-wrapper') || document.body;
    container.insertBefore(alertDiv, container.firstChild);
    
    // Auto dismiss after 5 seconds
    setTimeout(() => {
        alertDiv.remove();
    }, 5000);
}

