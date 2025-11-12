package giap.hcmute.vn.service;

import giap.hcmute.vn.model.User;

public interface UserService {
	User login(String username, String password);
	User get(String username);
}