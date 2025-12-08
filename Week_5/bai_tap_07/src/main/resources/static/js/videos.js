// Videos Management Module
let currentVideoEditId = null;
let videoSearchTimeout;

function initializeVideos() {
    const searchInput = document.getElementById('videoSearch');
    if (searchInput) {
        searchInput.addEventListener('input', function() {
            clearTimeout(videoSearchTimeout);
            videoSearchTimeout = setTimeout(() => {
                searchVideos();
            }, 500);
        });
    }
    
    const videoForm = document.getElementById('videoForm');
    if (videoForm) {
        videoForm.addEventListener('submit', handleVideoSubmit);
    }
}

async function loadVideos(search = '') {
    try {
        showLoading();
        const response = await VideoAPI.getAll(search);
        hideLoading();
        
        if (response.success && response.data) {
            const videos = response.data;
            const tbody = document.getElementById('videosTableBody');
            
            if (videos.length === 0) {
                tbody.innerHTML = '<tr><td colspan="7" class="text-center text-muted">Không có video nào</td></tr>';
                return;
            }
            
            tbody.innerHTML = videos.map(video => `
                <tr>
                    <td>${video.id || ''}</td>
                    <td>${video.title || ''}</td>
                    <td>
                        ${video.poster ? 
                            `<img src="${video.poster}" style="max-width: 100px; max-height: 100px;" class="img-thumbnail">` : 
                            '<span class="text-muted">Không có</span>'}
                    </td>
                    <td>${video.views || 0}</td>
                    <td>${video.category ? (video.category.categoryName || 'N/A') : 'N/A'}</td>
                    <td>
                        <span class="badge ${video.active ? 'bg-success' : 'bg-danger'}">
                            ${video.active ? 'Active' : 'Inactive'}
                        </span>
                    </td>
                    <td>
                        <button class="btn btn-sm btn-warning" onclick="editVideo(${video.id})">
                            <i class="bi bi-pencil"></i> Sửa
                        </button>
                        <button class="btn btn-sm btn-danger" onclick="deleteVideo(${video.id})">
                            <i class="bi bi-trash"></i> Xóa
                        </button>
                    </td>
                </tr>
            `).join('');
        }
    } catch (error) {
        hideLoading();
        console.error('Error loading videos:', error);
        showAlert('Không thể tải danh sách videos: ' + error.message, 'danger');
    }
}

function searchVideos() {
    const search = document.getElementById('videoSearch').value;
    loadVideos(search);
}

async function loadCategoriesForVideo() {
    try {
        const response = await CategoryAPI.getAll();
        if (response.success && response.data) {
            const select = document.getElementById('videoCategoryId');
            select.innerHTML = '<option value="">-- Chọn Category --</option>';
            response.data.forEach(category => {
                const option = document.createElement('option');
                option.value = category.id;
                option.textContent = category.categoryName;
                select.appendChild(option);
            });
        }
    } catch (error) {
        console.error('Error loading categories:', error);
    }
}

function openVideoModal(videoId = null) {
    currentVideoEditId = videoId;
    loadCategoriesForVideo();
    const modal = new bootstrap.Modal(document.getElementById('videoModal'));
    const form = document.getElementById('videoForm');
    
    if (videoId) {
        document.getElementById('videoModalTitle').textContent = 'Sửa Video';
        VideoAPI.getById(videoId).then(response => {
            if (response.success && response.data) {
                const video = response.data;
                document.getElementById('videoId').value = video.id;
                document.getElementById('videoTitle').value = video.title || '';
                document.getElementById('videoPoster').value = video.poster || '';
                document.getElementById('videoViews').value = video.views || 0;
                document.getElementById('videoDescription').value = video.description || '';
                document.getElementById('videoActive').value = video.active ? 'true' : 'false';
                if (video.category) {
                    document.getElementById('videoCategoryId').value = video.category.id;
                }
            }
        });
    } else {
        document.getElementById('videoModalTitle').textContent = 'Thêm Video mới';
        form.reset();
        document.getElementById('videoId').value = '';
        document.getElementById('videoViews').value = 0;
    }
    
    modal.show();
}

async function handleVideoSubmit(e) {
    e.preventDefault();
    
    const video = {
        title: document.getElementById('videoTitle').value,
        poster: document.getElementById('videoPoster').value || null,
        views: parseInt(document.getElementById('videoViews').value) || 0,
        description: document.getElementById('videoDescription').value || null,
        active: document.getElementById('videoActive').value === 'true'
    };
    
    const categoryId = document.getElementById('videoCategoryId').value;
    
    try {
        showLoading();
        let response;
        if (currentVideoEditId) {
            video.id = currentVideoEditId;
            response = await VideoAPI.update(currentVideoEditId, video);
        } else {
            response = await VideoAPI.create(video, categoryId || null);
        }
        hideLoading();
        
        if (response.success) {
            showAlert(currentVideoEditId ? 'Cập nhật video thành công' : 'Tạo video thành công', 'success');
            bootstrap.Modal.getInstance(document.getElementById('videoModal')).hide();
            loadVideos();
            if (typeof loadDashboard === 'function') {
                loadDashboard();
            }
        }
    } catch (error) {
        hideLoading();
        console.error('Error saving video:', error);
        showAlert('Lỗi: ' + error.message, 'danger');
    }
}

async function editVideo(id) {
    openVideoModal(id);
}

async function deleteVideo(id) {
    if (!confirm('Bạn có chắc chắn muốn xóa video này?')) {
        return;
    }
    
    try {
        showLoading();
        const response = await VideoAPI.delete(id);
        hideLoading();
        
        if (response.success) {
            showAlert('Xóa video thành công', 'success');
            loadVideos();
            if (typeof loadDashboard === 'function') {
                loadDashboard();
            }
        }
    } catch (error) {
        hideLoading();
        console.error('Error deleting video:', error);
        showAlert('Không thể xóa video: ' + error.message, 'danger');
    }
}

