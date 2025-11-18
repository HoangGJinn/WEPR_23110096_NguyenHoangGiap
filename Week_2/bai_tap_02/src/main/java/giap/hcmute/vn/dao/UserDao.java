package giap.hcmute.vn.dao;

import giap.hcmute.vn.model.User;

public interface UserDao {
	User get(String name);
	
	void insert(User user);
	boolean checkExistEmail(String email);
	boolean checkExistUsername(String username);
	boolean checkExistPhone(String phone);
	void updatePasswordByEmail(String email, String newPassword);

}
