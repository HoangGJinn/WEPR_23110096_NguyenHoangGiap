package giap.hcmute.vn.dao;

import java.util.List;
import giap.hcmute.vn.model.Category;

public interface CategoryDao {
    void insert(Category category);
    void edit(Category category);
    void delete(int cateid);
    Category get(int cateid);
    Category get(String catename);
    List<Category> getAll();
    List<Category> search(String keyword);
}
