package giap.hcmute.vn.service.impl;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import giap.hcmute.vn.model.CategoryEntity;
import giap.hcmute.vn.model.UserEntity;
import giap.hcmute.vn.mapper.CategoryMapper;
import giap.hcmute.vn.dto.CategoryDTO;
import giap.hcmute.vn.repository.CategoryRepository;
import giap.hcmute.vn.repository.impl.CategoryRepositoryImpl;
import giap.hcmute.vn.repository.impl.UserRepositoryImpl;
import giap.hcmute.vn.service.CategoryService;
import giap.hcmute.vn.repository.UserRepository;


public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public void insert(CategoryDTO dto) {

        // 1. Convert DTO → Entity
        CategoryEntity cate = CategoryMapper.toEntity(dto);

        // 2. Tìm user của category
        UserEntity user = userRepository.findById(dto.getUserId())
                                        .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Set quan hệ 2 chiều
        cate.setUser(user);
        user.getCategories().add(cate);

        // 4. Save category (cascade cũng sẽ hoạt động)
        categoryRepository.save(cate);
    }


    @Override
    public void edit(CategoryDTO newCategoryDTO){
        Optional<CategoryEntity> optionalEntity = categoryRepository.findById(newCategoryDTO.getCateid());
        
        if (!optionalEntity.isPresent()) {
            throw new RuntimeException("Category not found with id: " + newCategoryDTO.getCateid());
        }
        
        CategoryEntity oldEntity = optionalEntity.get();
        
        // Nếu có upload icon mới, xóa ảnh cũ trước
        if (newCategoryDTO.getIcon() != null && !newCategoryDTO.getIcon().isEmpty()) {
            // Xóa ảnh cũ nếu có
            if (oldEntity.getIcon() != null && !oldEntity.getIcon().isEmpty()) {
                // Lấy đường dẫn webapp (runtime path)
                String webappPath = System.getProperty("catalina.base");
                if (webappPath != null) {
                    File oldFile = new File(webappPath + File.separator + "webapps" + 
                                           File.separator + "baitap01" + File.separator + 
                                           oldEntity.getIcon());
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }
            }
        }
        
        // Sử dụng Mapper để update entity
        CategoryMapper.updateEntity(oldEntity, newCategoryDTO);
        
        // Nếu userId thay đổi, cập nhật quan hệ
        if (newCategoryDTO.getUserId() > 0) {
            UserEntity newUser = userRepository.findById(newCategoryDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Xóa khỏi user cũ nếu cần
            if (oldEntity.getUser() != null && oldEntity.getUser().getId() != newCategoryDTO.getUserId()) {
                oldEntity.getUser().getCategories().remove(oldEntity);
            }
            
            // Set user mới
            oldEntity.setUser(newUser);
            if (!newUser.getCategories().contains(oldEntity)) {
                newUser.getCategories().add(oldEntity);
            }
        }
        
        categoryRepository.update(oldEntity);
    }

    @Override
    public void delete(int cateid){
        categoryRepository.deleteById(cateid);
    }

    @Override
    public CategoryDTO get(int cateid){
        Optional<CategoryEntity> entity = categoryRepository.findById(cateid);
        return entity.map(CategoryMapper::toDTO).orElse(null);
    }

    @Override
    public CategoryDTO get(String catename){
        Optional<CategoryEntity> entity = categoryRepository.findByName(catename);
        return entity.map(CategoryMapper::toDTO).orElse(null);
    }

    @Override
    public List<CategoryDTO> getAll(){
        List<CategoryEntity> entities = categoryRepository.findAll();
        return entities.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> search(String keyword){
        List<CategoryEntity> entities = categoryRepository.search(keyword);
        return entities.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CategoryDTO> getByUserId(int userId) {
        List<CategoryEntity> entities = categoryRepository.findByUserId(userId);
        return entities.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
