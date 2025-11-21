package giap.hcmute.vn.mapper;

import giap.hcmute.vn.model.CategoryEntity;
import giap.hcmute.vn.dto.CategoryDTO;

/**
 * Mapper để convert giữa CategoryDTO và CategoryEntity (JPA Entity)
 */
public class CategoryMapper {
    
    /**
     * Convert từ CategoryEntity sang CategoryDTO
     */
    public static CategoryDTO toDTO(CategoryEntity entity) {
        if (entity == null) {
            return null;
        }
        
        CategoryDTO dto = new CategoryDTO();
        dto.setCateid(entity.getCateid());
        dto.setCatename(entity.getCatename());
        dto.setIcon(entity.getIcon());
        
        // Map userId từ quan hệ ManyToOne
        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }
        
        return dto;
    }
    
    /**
     * Convert từ CategoryDTO sang CategoryEntity
     * Lưu ý: User relationship sẽ được set ở Service layer
     */
    public static CategoryEntity toEntity(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }
        
        CategoryEntity entity = new CategoryEntity();
        entity.setCateid(dto.getCateid());
        entity.setCatename(dto.getCatename());
        entity.setIcon(dto.getIcon());
        // User relationship được set trong service layer
        
        return entity;
    }
    
    /**
     * Update một entity có sẵn từ DTO (dùng cho update operations)
     * Chỉ update các field không null và không rỗng từ DTO
     */
    public static void updateEntity(CategoryEntity entity, CategoryDTO dto) {
        if (entity == null || dto == null) {
            return;
        }
        
        // Tên CategoryDTO: bắt buộc phải có và không được rỗng
        if (dto.getCatename() != null && !dto.getCatename().trim().isEmpty()) {
            entity.setCatename(dto.getCatename());
        }
        
        // Icon cho phép null hoặc rỗng (trường hợp không upload icon mới)
        // Nếu dto.getIcon() != null thì mới update
        if (dto.getIcon() != null) {
            // Nếu là chuỗi rỗng, vẫn cho phép (có thể muốn xóa icon)
            entity.setIcon(dto.getIcon());
        }
        
        // cateid KHÔNG bao giờ update (primary key không được thay đổi)
    }
}
