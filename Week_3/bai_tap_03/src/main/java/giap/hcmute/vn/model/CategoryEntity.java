package giap.hcmute.vn.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cateid")
    private int cateid;
    
    @Column(name = "catename", nullable = false, length = 100)
    private String catename;
    
    @Column(name = "icon", length = 255)
    private String icon;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id")
    // @JoinColumn(name = "userid") // original version 
    private UserEntity user;
}
