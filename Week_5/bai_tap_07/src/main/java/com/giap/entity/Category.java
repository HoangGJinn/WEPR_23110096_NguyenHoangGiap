package com.giap.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Video> videos;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
