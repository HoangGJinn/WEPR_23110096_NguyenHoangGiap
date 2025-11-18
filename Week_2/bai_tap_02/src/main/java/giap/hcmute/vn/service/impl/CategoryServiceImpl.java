package giap.hcmute.vn.service.impl;

import java.io.File;
import java.util.List;

import giap.hcmute.vn.dao.CategoryDao;
import giap.hcmute.vn.dao.impl.CategoryDaoImpl;
import giap.hcmute.vn.model.Category;
import giap.hcmute.vn.service.CategoryService;


public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public void insert(Category category){
        categoryDao.insert(category);
    }

    @Override
    public void edit(Category newCategory){
        Category oldCategory = categoryDao.get(newCategory.getCateid());
        
        if (oldCategory == null) {
            throw new RuntimeException("Category not found with id: " + newCategory.getCateid());
        }
        
        // Update tên
        oldCategory.setCatename(newCategory.getCatename());
        
        // Nếu có upload icon mới
        if (newCategory.getIcon() != null && !newCategory.getIcon().isEmpty()) {
            // Xóa ảnh cũ nếu có
            if (oldCategory.getIcon() != null && !oldCategory.getIcon().isEmpty()) {
                // Lấy đường dẫn webapp (runtime path)
                String webappPath = System.getProperty("catalina.base");
                if (webappPath != null) {
                    File oldFile = new File(webappPath + File.separator + "webapps" + 
                                           File.separator + "baitap01" + File.separator + 
                                           oldCategory.getIcon());
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }
            }
            
            // Set icon mới
            oldCategory.setIcon(newCategory.getIcon());
        }
        
        categoryDao.edit(oldCategory);
    }

    @Override
    public void delete(int cateid){
        categoryDao.delete(cateid);
    }

    @Override
    public Category get(int cateid){
        return categoryDao.get(cateid);
    }

    @Override
    public Category get(String catename){
        return categoryDao.get(catename);
    }

    @Override
    public List<Category> getAll(){
        return categoryDao.getAll();
    }

    @Override
    public List<Category> search(String keyword){
        return categoryDao.search(keyword);
    }
}
