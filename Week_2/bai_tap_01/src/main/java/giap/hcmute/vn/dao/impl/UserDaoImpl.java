package giap.hcmute.vn.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import giap.hcmute.vn.config.DBMySqlConnection;
import giap.hcmute.vn.dao.UserDao;
import giap.hcmute.vn.model.User;

public class UserDaoImpl implements UserDao{
	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	//Viết hàm xử lý DAO tại đây
	
	@Override
	public User get(String username) {
	String sql = "SELECT * FROM [User] WHERE username = ? ";
	try {
		conn = new DBMySqlConnection().getConnection();
		ps = conn.prepareStatement(sql);
		ps.setString(1, username);
		rs = ps.executeQuery();
		while (rs.next()) {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setEmail(rs.getString("email"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			return user; 
		}
	} catch (Exception e) {e.printStackTrace(); }
		return null;
	}
}
