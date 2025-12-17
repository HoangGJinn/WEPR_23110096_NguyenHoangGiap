package com.giap.baitap08.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(min = 3, max = 200, message = "Tiêu đề phải từ 3 đến 200 ký tự")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng phải lớn hơn hoặc bằng 0")
    @Max(value = 10000, message = "Số lượng không được vượt quá 10,000")
    @Column(nullable = false)
    private Integer quantity;

    @Size(max = 500, message = "Mô tả không được vượt quá 500 ký tự")
    @Column(columnDefinition = "TEXT")
    private String desc;

    @NotNull(message = "Giá không được để trống")
    @Positive(message = "Giá phải lớn hơn 0")
    @Max(value = 999999999, message = "Giá không được vượt quá 999,999,999")
    @Column(nullable = false)
    private Double price;

    @Column(columnDefinition = "TEXT")
    private String images;

    @NotNull(message = "User ID không được để trống")
    @Positive(message = "User ID phải lớn hơn 0")
    @Column(name = "user_id", nullable = false)
    private Long userid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}

