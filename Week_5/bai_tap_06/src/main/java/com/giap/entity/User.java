package com.giap.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Username không được để trống")
    @Size(min = 3, max = 50, message = "Username phải từ 3 đến 50 ký tự")
    private String username;

    @NotBlank(message = "Password không được để trống")
    @Size(min = 3, message = "Password phải có ít nhất 3 ký tự")
    private String password;

    private String phone;

    private String fullname;

    @Email(message = "Email không hợp lệ")
    private String email;

    private Boolean admin;

    private Boolean active;

    private String images;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Category> categories;
}
