package com.giap.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Tên category không được để trống")
    @Size(max = 100, message = "Tên category không được quá 100 ký tự")
    private String categoryName;

    @NotBlank(message = "Mã category không được để trống")
    @Size(max = 50, message = "Mã category không được quá 50 ký tự")
    private String categoryCode;

    private String images;

    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
