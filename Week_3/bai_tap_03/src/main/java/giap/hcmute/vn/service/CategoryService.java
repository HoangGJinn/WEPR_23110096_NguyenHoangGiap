package giap.hcmute.vn.service;

import giap.hcmute.vn.dto.CategoryDTO;
import java.util.List;

public interface CategoryService {
    void insert(CategoryDTO CategoryDTO);
    void edit(CategoryDTO CategoryDTO);
    void delete(int cateid);
    CategoryDTO get(int cateid);
    CategoryDTO get(String catename);
    List<CategoryDTO> getAll();
    List<CategoryDTO> search(String keyword);
    List<CategoryDTO> getByUserId(int userId);
}
