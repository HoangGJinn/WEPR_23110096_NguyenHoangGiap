package giap.hcmute.vn.service;

import giap.hcmute.vn.dto.UserDTO;

public interface UserService {
	UserDTO login(String username, String password);
	UserDTO get(String username);
	
	void insert(UserDTO UserDTO);
	boolean register(String email, String password, String username, String fullname, String phone);
	boolean checkExistEmail(String email);
	boolean checkExistUsername(String username);
	boolean checkExistPhone(String phone);
	void updatePasswordByEmail(String email, String newPassword);
	
	/**
	 * Đổi mật khẩu cho user đang logged in
	 * @param userId ID của user
	 * @param oldPassword Mật khẩu cũ
	 * @param newPassword Mật khẩu mới
	 * @return true nếu đổi thành công, false nếu mật khẩu cũ không đúng
	 */
	boolean changePassword(int userId, String oldPassword, String newPassword);
}