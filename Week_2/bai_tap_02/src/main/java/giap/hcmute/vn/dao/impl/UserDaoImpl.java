package giap.hcmute.vn.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Date;

import giap.hcmute.vn.config.DBMySqlConnection;
import giap.hcmute.vn.dao.UserDao;
import giap.hcmute.vn.model.User;

public class UserDaoImpl implements UserDao {

    private static final String SELECT_BY_USERNAME =
        "SELECT id, email, username, fullname, password, avatar, roleid, phone, createdDate " +
        "FROM `user` WHERE username = ? LIMIT 1";

    @Override
    public User get(String username) {
        if (username == null) return null;
        // optional: trim
        String userParam = username.trim();

        try (Connection conn = DBMySqlConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_USERNAME)) {

            ps.setString(1, userParam);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setFullname(rs.getString("fullname"));
                    user.setPassword(rs.getString("password"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setRoleid(rs.getInt("roleid"));
                    user.setPhone(rs.getString("phone"));

                    Timestamp ts = rs.getTimestamp("createdDate");
                    if (ts != null) {
                        user.setCreatedDate(new Date(ts.getTime()));
                    }
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void insert(User user) {
        String sql = "INSERT INTO `user` (email, username, fullname, password, avatar, roleid, phone, createdDate) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBMySqlConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getFullname());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getAvatar());
            ps.setInt(6, user.getRoleid());
            ps.setString(7, user.getPhone());
            ps.setTimestamp(8, new java.sql.Timestamp(user.getCreatedDate().getTime()));

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean checkExistEmail(String email) {
        String query = "SELECT * FROM `user` WHERE email = ?";
        try (Connection conn = DBMySqlConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean checkExistUsername(String username) {
        String query = "SELECT * FROM `user` WHERE username = ?";
        try (Connection conn = DBMySqlConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkExistPhone(String phone) {
        String query = "SELECT * FROM `user` WHERE phone = ?";
        try (Connection conn = DBMySqlConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public void updatePasswordByEmail(String email, String newPassword) {
        String sql = "UPDATE `user` SET password = ? WHERE email = ?";
        try (Connection conn = DBMySqlConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setString(2, email);

            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new RuntimeException("Không tìm thấy email: " + email);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi update password: " + e.getMessage(), e);
        }
    }
}