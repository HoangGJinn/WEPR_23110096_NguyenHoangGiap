package giap.hcmute.vn.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;
    
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(name = "fullname", length = 100)
    private String fullname;
    
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    @Column(name = "avatar", length = 255)
    private String avatar;
    
    @Column(name = "roleid")
    private int roleid;
    
    @Column(name = "phone", length = 20)
    private String phone;
    
    @Column(name = "createdDate")
    private Date createdDate;

    @OneToMany(
        mappedBy = "user", // tên thuộc tính trong CategoryEntity tham chiếu đến UserEntity
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<CategoryEntity> categories = new ArrayList<>();
}
