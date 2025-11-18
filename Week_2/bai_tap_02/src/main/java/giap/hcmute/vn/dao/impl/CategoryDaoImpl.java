package giap.hcmute.vn.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import giap.hcmute.vn.config.DBMySqlConnection;
import giap.hcmute.vn.model.Category;

public class CategoryDaoImpl implements giap.hcmute.vn.dao.CategoryDao {

    @Override
    public void insert(Category category) {
        String sql = "INSERT INTO category (cate_name, icons) VALUES (?, ?)";
        try (Connection conn = DBMySqlConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, category.getCatename());
            ps.setString(2, category.getIcon());
            ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error inserting category: " + e.getMessage(), e);
        }
    }

    @Override
    public void edit(Category category) {
        String sql = "UPDATE category SET cate_name = ?, icons = ? WHERE cate_id = ?";
        try (Connection conn = DBMySqlConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, category.getCatename());
                ps.setString(2, category.getIcon());
                ps.setInt(3, category.getCateid());
                ps.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating category: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int cateid) {
        String sql = "DELETE FROM category WHERE cate_id = ?";
        try (Connection conn = DBMySqlConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, cateid);
            ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting category: " + e.getMessage(), e);
        }
    }

    @Override
    public Category get(int cateid) {
        String sql = "SELECT cate_id, cate_name, icons FROM category WHERE cate_id = ? LIMIT 1";
        try (Connection conn = DBMySqlConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, cateid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category();
                    category.setCateid(rs.getInt("cate_id"));
                    category.setCatename(rs.getString("cate_name"));
                    category.setIcon(rs.getString("icons"));
                    return category;
                } else {
                    return null; // Không tìm thấy category
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting category: " + e.getMessage(), e);
        }
    }

    @Override
    public Category get(String catename) {
        String sql = "SELECT cate_id, cate_name, icons FROM category WHERE cate_name = ?";
        try (Connection conn = DBMySqlConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, catename);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category();
                    category.setCateid(rs.getInt("cate_id"));
                    category.setCatename(rs.getString("cate_name"));
                    category.setIcon(rs.getString("icons"));
                    return category;
                } else {
                    return null; // Không tìm thấy category
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting category: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Category> getAll() {
        String sql = "SELECT cate_id, cate_name, icons FROM category";
        try (Connection conn = DBMySqlConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            List<Category> categories = new ArrayList<>();
            while (rs.next()) {
                Category category = new Category();
                category.setCateid(rs.getInt("cate_id"));
                category.setCatename(rs.getString("cate_name"));
                category.setIcon(rs.getString("icons"));
                categories.add(category);
            }
            return categories;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting all categories: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Category> search(String keyword) {
        String sql = "SELECT cate_id, cate_name, icons FROM category WHERE cate_name LIKE ?";
        try (Connection conn = DBMySqlConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "%" + keyword + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                List<Category> categories = new ArrayList<>();
                while (rs.next()) {
                    Category category = new Category();
                    category.setCateid(rs.getInt("cate_id"));
                    category.setCatename(rs.getString("cate_name"));
                    category.setIcon(rs.getString("icons"));
                    categories.add(category);
                }
                return categories;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error searching categories: " + e.getMessage(), e);
        }
    }
    
}
