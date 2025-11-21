package giap.hcmute.vn.repository;

import giap.hcmute.vn.model.CategoryEntity;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    
    /**
     * Tìm CategoryDTO theo ID
     */
    Optional<CategoryEntity> findById(int cateid);
    
    /**
     * Tìm CategoryDTO theo tên
     */
    Optional<CategoryEntity> findByName(String catename);
    
    /**
     * Lấy tất cả categories
     */
    List<CategoryEntity> findAll();
    
    /**
     * Tìm kiếm CategoryDTO theo keyword
     */
    List<CategoryEntity> search(String keyword);
    
    /**
     * Thêm CategoryDTO mới
     */
    void save(CategoryEntity CategoryDTO);
    
    /**
     * Cập nhật CategoryDTO
     */
    void update(CategoryEntity CategoryDTO);
    
    /**
     * Xóa CategoryDTO theo ID
     */
    void deleteById(int cateid);
    
    /**
     * Đếm tổng số CategoryDTO
     */
    long count();
    
    /**
     * Tìm tất cả categories theo userId
     */
    List<CategoryEntity> findByUserId(int userId);
}
