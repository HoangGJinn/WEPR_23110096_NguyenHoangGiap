package giap.hcmute.vn.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String username;
    private String email;
    private String fullname;
    // private String password;
    private String avatar;
    private int roleid;
    private String phone;
    private Date createdDate;

    // Thêm mối quan hệ OneToMany dưới dạng DTO
    private List<CategoryDTO> categories;

}
