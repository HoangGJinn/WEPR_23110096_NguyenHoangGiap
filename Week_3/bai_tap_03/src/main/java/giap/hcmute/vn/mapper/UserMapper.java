package giap.hcmute.vn.mapper;

import giap.hcmute.vn.model.UserEntity;
import giap.hcmute.vn.dto.UserDTO;
import giap.hcmute.vn.model.CategoryEntity;
import giap.hcmute.vn.dto.CategoryDTO;

import java.util.stream.Collectors;

public class UserMapper {
    
    /**
     * Convert từ UserEntity sang UserDTO (bao gồm categories)
     */
    public static UserDTO toDTO(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setFullname(entity.getFullname());
        dto.setAvatar(entity.getAvatar());
        dto.setRoleid(entity.getRoleid());
        dto.setPhone(entity.getPhone());
        dto.setCreatedDate(entity.getCreatedDate());

        // ⭐ Map CategoryEntity → CategoryDTO
        if (entity.getCategories() != null) {
            dto.setCategories(
                    entity.getCategories().stream()
                            .map(CategoryMapper::toDTO)
                            .collect(Collectors.toList())
            );
        }
        
        return dto;
    }
    
    /**
     * Convert từ UserDTO sang UserEntity
     * (CATEGORY KHÔNG map vì tránh vòng lặp và vì Entity luôn được tạo từ Service)
     */
    public static UserEntity toEntity(UserDTO model) {
        if (model == null) {
            return null;
        }
        
        UserEntity entity = new UserEntity();
        entity.setId(model.getId());
        entity.setUsername(model.getUsername());
        entity.setEmail(model.getEmail());
        entity.setFullname(model.getFullname());
        entity.setAvatar(model.getAvatar());
        entity.setRoleid(model.getRoleid());
        entity.setPhone(model.getPhone());
        entity.setCreatedDate(model.getCreatedDate());
        // Category xử lý riêng trong service layer
        
        return entity;
    }
    
    /**
     * Update entity có sẵn bằng DTO
     * KHÔNG update categories tại đây.
     */
    public static void updateEntity(UserEntity entity, UserDTO model) {
        if (entity == null || model == null) {
            return;
        }
        
        if (model.getUsername() != null && !model.getUsername().trim().isEmpty()) {
            entity.setUsername(model.getUsername());
        }
        
        if (model.getEmail() != null && !model.getEmail().trim().isEmpty()) {
            entity.setEmail(model.getEmail());
        }
        
        if (model.getFullname() != null && !model.getFullname().trim().isEmpty()) {
            entity.setFullname(model.getFullname());
        }
        
        if (model.getAvatar() != null) {
            entity.setAvatar(model.getAvatar());
        }
        
        if (model.getRoleid() > 0) {
            entity.setRoleid(model.getRoleid());
        }
        
        if (model.getPhone() != null) {
            entity.setPhone(model.getPhone());
        }
        
        // → Categories được xử lý bởi CategoryService hoặc UserService riêng
    }
}
