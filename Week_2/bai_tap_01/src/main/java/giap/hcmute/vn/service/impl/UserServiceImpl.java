package giap.hcmute.vn.service.impl;

import giap.hcmute.vn.dao.UserDao;
import giap.hcmute.vn.dao.impl.UserDaoImpl;
import giap.hcmute.vn.model.User;
import giap.hcmute.vn.service.UserService;

public class UserServiceImpl implements UserService {
	UserDao userDao = new UserDaoImpl();
	
	@Override
	public User login(String username, String password) {
		User user = this.get(username);
		if (user != null && password.equals(user.getPassword())) {
			return user;
		}
	
	return null;
	}
	@Override
	public User get(String username) {
		return userDao.get(username);
	}

}
