package giap.hcmute.vn.repository;

import giap.hcmute.vn.model.UserEntity;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    
    /**
     * Tìm UserDTO theo ID
     */
    Optional<UserEntity> findById(int id);
    
    /**
     * Tìm UserDTO theo username
     */
    Optional<UserEntity> findByUsername(String username);
    
    /**
     * Tìm UserDTO theo email
     */
    Optional<UserEntity> findByEmail(String email);
    
    /**
     * Lấy tất cả users
     */
    List<UserEntity> findAll();
    
    /**
     * Thêm UserDTO mới
     */
    void save(UserEntity UserDTO);
    
    /**
     * Cập nhật UserDTO
     */
    void update(UserEntity UserDTO);
    
    /**
     * Xóa UserDTO theo ID
     */
    void deleteById(int id);
    
    /**
     * Kiểm tra username đã tồn tại chưa
     */
    boolean existsByUsername(String username);
    
    /**
     * Kiểm tra email đã tồn tại chưa
     */
    boolean existsByEmail(String email);
    
    /**
     * Kiểm tra phone đã tồn tại chưa
     */
    boolean existsByPhone(String phone);
    
    /**
     * Cập nhật password theo email
     */
    void updatePasswordByEmail(String email, String newPassword);
    
    /**
     * Xác thực password của user
     */
    boolean verifyPassword(String username, String password);
    
    /**
     * Cập nhật password theo user ID
     */
    void updatePassword(int userId, String newPassword);
}