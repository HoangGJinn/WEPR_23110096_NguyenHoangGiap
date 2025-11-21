package giap.hcmute.vn.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAConfig {
    
    private static EntityManagerFactory entityManagerFactory;
    
    // Khởi tạo EntityManagerFactory khi class được load
    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Failed to initialize EntityManagerFactory: " + e.getMessage());
        }
    }
    
    /**
     * Lấy EntityManager để thực hiện các thao tác với database
     * @return EntityManager instance
     */
    public static EntityManager getEntityManager() {
        if (entityManagerFactory == null) {
            throw new IllegalStateException("EntityManagerFactory is not initialized");
        }
        return entityManagerFactory.createEntityManager();
    }
    
    /**
     * Đóng EntityManagerFactory khi không còn sử dụng
     * Nên gọi method này khi shutdown ứng dụng
     */
    public static void close() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
    
    /**
     * Kiểm tra EntityManagerFactory có đang mở không
     * @return true nếu đang mở, false nếu đã đóng
     */
    public static boolean isOpen() {
        return entityManagerFactory != null && entityManagerFactory.isOpen();
    }
}
