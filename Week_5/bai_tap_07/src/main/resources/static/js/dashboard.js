// Dashboard Module
async function loadDashboard() {
    try {
        const response = await AdminAPI.getDashboard();
        if (response.success && response.data) {
            const stats = response.data;
            document.getElementById('statTotalUsers').textContent = stats.totalUsers || 0;
            document.getElementById('statActiveUsers').textContent = stats.activeUsers || 0;
            document.getElementById('statTotalCategories').textContent = stats.totalCategories || 0;
            document.getElementById('statActiveCategories').textContent = stats.activeCategories || 0;
            document.getElementById('statTotalVideos').textContent = stats.totalVideos || 0;
            document.getElementById('statActiveVideos').textContent = stats.activeVideos || 0;
            document.getElementById('statTotalAll').textContent = 
                (stats.totalUsers || 0) + (stats.totalCategories || 0) + (stats.totalVideos || 0);
        }
    } catch (error) {
        console.error('Error loading dashboard:', error);
        showAlert('Không thể tải thống kê dashboard', 'danger');
    }
}

