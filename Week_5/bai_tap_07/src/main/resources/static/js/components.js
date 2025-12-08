// Component Loader
async function loadComponent(path, containerId) {
    try {
        const response = await fetch(path);
        if (!response.ok) {
            throw new Error(`Failed to load ${path}: ${response.status}`);
        }
        const html = await response.text();
        const container = document.getElementById(containerId);
        if (container) {
            if (containerId === 'pages-container') {
                // Append pages instead of replacing
                container.insertAdjacentHTML('beforeend', html);
            } else {
                container.innerHTML = html;
            }
        }
        return html;
    } catch (error) {
        console.error(`Error loading component ${path}:`, error);
        return null;
    }
}

async function loadAllComponents() {
    try {
        // Load sidebar
        await loadComponent('/components/sidebar.html', 'sidebar-container');
        
        // Load header
        await loadComponent('/components/header.html', 'header-container');
        
        // Load footer
        await loadComponent('/components/footer.html', 'footer-container');
        
        // Load modals
        await loadComponent('/components/modals.html', 'modals-container');
        
        // Load all pages into pages container
        await loadComponent('/pages/dashboard.html', 'pages-container');
        await loadComponent('/pages/users.html', 'pages-container');
        await loadComponent('/pages/categories.html', 'pages-container');
        await loadComponent('/pages/videos.html', 'pages-container');
    } catch (error) {
        console.error('Error loading components:', error);
    }
}

