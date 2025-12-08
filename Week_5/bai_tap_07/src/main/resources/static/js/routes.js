// Routing Module
let currentPage = 'dashboard';

const pageTitles = {
    'dashboard': 'Dashboard',
    'users': 'Quản lý Users',
    'categories': 'Quản lý Categories',
    'videos': 'Quản lý Videos'
};

function initializeNavigation() {
    const navLinks = document.querySelectorAll('.nav-link');
    navLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            const page = this.getAttribute('data-page');
            navigateToPage(page);
        });
    });
}

function navigateToPage(page) {
    // Update active nav
    document.querySelectorAll('.nav-link').forEach(link => {
        link.classList.remove('active');
    });
    document.querySelector(`[data-page="${page}"]`).classList.add('active');
    
    // Hide all pages
    document.querySelectorAll('.page-content').forEach(pageEl => {
        pageEl.classList.remove('active');
    });
    
    // Show selected page
    document.getElementById(`${page}-page`).classList.add('active');
    
    // Update page title
    document.getElementById('pageTitle').textContent = pageTitles[page] || 'Dashboard';
    
    currentPage = page;
    
    // Reload data for the page
    if (page === 'dashboard' && typeof loadDashboard === 'function') {
        loadDashboard();
    } else if (page === 'users' && typeof loadUsers === 'function') {
        loadUsers();
    } else if (page === 'categories' && typeof loadCategories === 'function') {
        loadCategories();
    } else if (page === 'videos' && typeof loadVideos === 'function') {
        loadVideos();
    }
}

function getCurrentPage() {
    return currentPage;
}

