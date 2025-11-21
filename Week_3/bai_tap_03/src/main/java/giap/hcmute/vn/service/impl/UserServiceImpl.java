package giap.hcmute.vn.service.impl;

import giap.hcmute.vn.model.UserEntity;
import giap.hcmute.vn.mapper.UserMapper;
import giap.hcmute.vn.dto.UserDTO;
import giap.hcmute.vn.repository.UserRepository;
import giap.hcmute.vn.repository.impl.UserRepositoryImpl;
import giap.hcmute.vn.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public UserDTO login(String username, String password) {
        // Xác thực password trực tiếp từ repository
        if (userRepository.verifyPassword(username, password)) {
            return this.get(username);
        }
        return null;
    }

    @Override
    public UserDTO get(String username) {
        Optional<UserEntity> entity = userRepository.findByUsername(username);
        return entity.map(UserMapper::toDTO).orElse(null);
    }

    @Override
    public boolean register(String username, String password, String email,
                            String fullname, String phone) {

        // Nếu username đã tồn tại → thất bại
        if (userRepository.existsByUsername(username)) {
            return false;
        }

        // Lấy ngày hiện tại
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);

        // Tạo entity mới (roleid = 3)
        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setEmail(email);
        entity.setFullname(fullname);
        entity.setPhone(phone);
        entity.setRoleid(3);
        entity.setCreatedDate(date);

        userRepository.save(entity);
        return true;
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean checkExistPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public void insert(UserDTO UserDTO) {
        UserEntity entity = UserMapper.toEntity(UserDTO);
        userRepository.save(entity);
    }

    @Override
    public void updatePasswordByEmail(String email, String newPassword) {
        userRepository.updatePasswordByEmail(email, newPassword);
    }
    
    @Override
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        // Lấy user hiện tại để verify
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return false;
        }
        
        UserEntity user = userOpt.get();
        // Kiểm tra mật khẩu cũ có đúng không
        if (!oldPassword.equals(user.getPassword())) {
            return false;
        }
        
        // Cập nhật mật khẩu mới
        userRepository.updatePassword(userId, newPassword);
        return true;
    }
}