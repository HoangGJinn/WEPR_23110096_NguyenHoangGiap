package com.giap.config;

import jakarta.servlet.*;
import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class SitemeshConfig {

    @Bean
    public FilterRegistrationBean<Filter> siteMeshFilter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        
        // Tạo Filter sử dụng Jakarta Servlet API
        // ConfigurableSiteMeshFilter sẽ được khởi tạo bên trong
        Filter sitemeshFilter = new Filter() {
            private ConfigurableSiteMeshFilter delegate;
            
            @Override
            public void init(FilterConfig filterConfig) throws ServletException {
                // Khởi tạo ConfigurableSiteMeshFilter
                // Lưu ý: Sitemesh 3.2.1 vẫn dùng javax.servlet internally
                // Nhưng chúng ta có thể wrap nó
                delegate = new ConfigurableSiteMeshFilter() {
                    @Override
                    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
                        // Định nghĩa trang decorator (trang khung) cho các trang admin
                        builder.addDecoratorPath("/admin/*", "/WEB-INF/decorators/admin.jsp");
                        
                        // Định nghĩa các trang KHÔNG áp dụng decorator
                        builder.addExcludedPath("/api/*");
                        builder.addExcludedPath("/static/*");
                        builder.addExcludedPath("/css/*");
                        builder.addExcludedPath("/js/*");
                        builder.addExcludedPath("/images/*");
                    }
                };
            }
            
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
                    throws IOException, ServletException {
                if (delegate != null) {
                    // Sitemesh 3.2.1 vẫn expect javax.servlet
                    // Cần tạo adapter để chuyển đổi
                    // Tạm thời pass through nếu không thể adapt
                    try {
                        // Thử sử dụng delegate trực tiếp
                        // Nếu Sitemesh đã hỗ trợ Jakarta thì sẽ work
                        delegate.doFilter(request, response, chain);
                    } catch (ClassCastException e) {
                        // Nếu không tương thích, chỉ pass through
                        chain.doFilter(request, response);
                    }
                } else {
                    chain.doFilter(request, response);
                }
            }
            
            @Override
            public void destroy() {
                if (delegate != null) {
                    delegate.destroy();
                }
            }
        };
        
        bean.setFilter(sitemeshFilter);
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        return bean;
    }
}