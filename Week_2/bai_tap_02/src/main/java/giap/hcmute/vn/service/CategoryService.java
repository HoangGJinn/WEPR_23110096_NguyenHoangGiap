package giap.hcmute.vn.service;

import giap.hcmute.vn.model.Category;
import java.util.List;

public interface CategoryService {
    void insert(Category category);
    void edit(Category category);
    void delete(int cateid);
    Category get(int cateid);
    Category get(String catename);
    List<Category> getAll();
    List<Category> search(String keyword);
}
