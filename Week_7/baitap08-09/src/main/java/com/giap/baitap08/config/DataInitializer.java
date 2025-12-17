package com.giap.baitap08.config;

import com.giap.baitap08.entity.Category;
import com.giap.baitap08.entity.Product;
import com.giap.baitap08.entity.User;
import com.giap.baitap08.entity.UserRole;
import com.giap.baitap08.repository.CategoryRepository;
import com.giap.baitap08.repository.ProductRepository;
import com.giap.baitap08.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Bean
    public ApplicationRunner initializeData() {
        return args -> {
            // Kiểm tra xem đã có dữ liệu chưa
            if (categoryRepository.count() > 0) {
                return; // Đã có dữ liệu, không cần khởi tạo lại
            }

            // Tạo Categories
            Category category1 = new Category();
            category1.setName("Điện tử");
            category1.setImages("https://example.com/images/electronics.jpg");
            category1 = categoryRepository.save(category1);

            Category category2 = new Category();
            category2.setName("Thời trang");
            category2.setImages("https://example.com/images/fashion.jpg");
            category2 = categoryRepository.save(category2);

            Category category3 = new Category();
            category3.setName("Sách");
            category3.setImages("https://example.com/images/books.jpg");
            category3 = categoryRepository.save(category3);

            Category category4 = new Category();
            category4.setName("Thể thao");
            category4.setImages("https://example.com/images/sports.jpg");
            category4 = categoryRepository.save(category4);

            // Tạo ADMIN user
            User admin = new User();
            admin.setFullname("Admin System");
            admin.setEmail("admin@gmail.com");
            admin.setPassword("Admin@123");
            admin.setPhone("0123456789");
            admin.setRole(UserRole.ADMIN);
            Set<Category> adminCategories = new HashSet<>();
            adminCategories.add(category1);
            adminCategories.add(category2);
            adminCategories.add(category3);
            adminCategories.add(category4);
            admin.setCategories(adminCategories);
            admin = userRepository.save(admin);

            // Tạo USER thông thường
            User user1 = new User();
            user1.setFullname("Nguyễn Văn A");
            user1.setEmail("user@gmail.com");
            user1.setPassword("User@123");
            user1.setPhone("0987654321");
            user1.setRole(UserRole.USER);
            Set<Category> categories1 = new HashSet<>();
            categories1.add(category1);
            categories1.add(category2);
            user1.setCategories(categories1);
            user1 = userRepository.save(user1);

            User user2 = new User();
            user2.setFullname("Trần Thị B");
            user2.setEmail("tranthib@example.com");
            user2.setPassword("password456");
            user2.setPhone("0912345678");
            user2.setRole(UserRole.USER);
            Set<Category> categories2 = new HashSet<>();
            categories2.add(category2);
            categories2.add(category3);
            user2.setCategories(categories2);
            user2 = userRepository.save(user2);

            User user3 = new User();
            user3.setFullname("Lê Văn C");
            user3.setEmail("levanc@example.com");
                user3.setPassword("password789");
            user3.setPhone("0901234567");
            user3.setRole(UserRole.USER);
            Set<Category> categories3 = new HashSet<>();
            categories3.add(category1);
            categories3.add(category4);
            user3.setCategories(categories3);
            user3 = userRepository.save(user3);

            User user4 = new User();
            user4.setFullname("Phạm Thị D");
            user4.setEmail("phamthid@example.com");
            user4.setPassword("password012");
            user4.setPhone("0900123456");
            user4.setRole(UserRole.USER);
            Set<Category> categories4 = new HashSet<>();
            categories4.add(category3);
            categories4.add(category4);
            user4.setCategories(categories4);
            user4 = userRepository.save(user4);

            // Tạo Products
            Product product1 = new Product();
            product1.setTitle("Laptop Dell XPS 15");
            product1.setQuantity(10);
            product1.setDesc("Laptop cao cấp với màn hình 15 inch, RAM 16GB, SSD 512GB");
            product1.setPrice(25000000.0);
            product1.setImages("https://images.unsplash.com/photo-1593642532400-2682810df593?w=400");
            product1.setUserid(user1.getId());
            productRepository.save(product1);

            Product product2 = new Product();
            product2.setTitle("iPhone 15 Pro Max");
            product2.setQuantity(5);
            product2.setDesc("Điện thoại thông minh cao cấp với camera 48MP");
            product2.setPrice(35000000.0);
            product2.setImages("https://images.unsplash.com/photo-1678685888221-cda773a3dcdb?w=400");
            product2.setUserid(user1.getId());
            productRepository.save(product2);

            Product product3 = new Product();
            product3.setTitle("Áo sơ mi nam");
            product3.setQuantity(50);
            product3.setDesc("Áo sơ mi nam chất liệu cotton cao cấp");
            product3.setPrice(500000.0);
            product3.setImages("https://images.unsplash.com/photo-1602810318383-e386cc2a3ccf?w=400");
            product3.setUserid(user2.getId());
            productRepository.save(product3);

            Product product4 = new Product();
            product4.setTitle("Váy đầm nữ");
            product4.setQuantity(30);
            product4.setDesc("Váy đầm nữ thiết kế hiện đại, phong cách");
            product4.setPrice(800000.0);
            product4.setImages("https://images.unsplash.com/photo-1595777457583-95e059d581b8?w=400");
            product4.setUserid(user2.getId());
            productRepository.save(product4);

            Product product5 = new Product();
            product5.setTitle("Sách 'Đắc Nhân Tâm'");
            product5.setQuantity(100);
            product5.setDesc("Cuốn sách nổi tiếng về nghệ thuật giao tiếp");
            product5.setPrice(120000.0);
            product5.setImages("https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=400");
            product5.setUserid(user3.getId());
            productRepository.save(product5);

            Product product6 = new Product();
            product6.setTitle("Sách 'Nhà Giả Kim'");
            product6.setQuantity(80);
            product6.setDesc("Tiểu thuyết nổi tiếng của Paulo Coelho");
            product6.setPrice(150000.0);
            product6.setImages("https://images.unsplash.com/photo-1512820790803-83ca734da794?w=400");
            product6.setUserid(user3.getId());
            productRepository.save(product6);

            Product product7 = new Product();
            product7.setTitle("Giày thể thao Nike");
            product7.setQuantity(25);
            product7.setDesc("Giày thể thao Nike Air Max chính hãng");
            product7.setPrice(2500000.0);
            product7.setImages("https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400");
            product7.setUserid(user4.getId());
            productRepository.save(product7);

            Product product8 = new Product();
            product8.setTitle("Bóng đá Adidas");
            product8.setQuantity(40);
            product8.setDesc("Bóng đá chính hãng Adidas size 5");
            product8.setPrice(500000.0);
            product8.setImages("https://images.unsplash.com/photo-1614632537197-38a17061c2bd?w=400");
            product8.setUserid(user4.getId());
            productRepository.save(product8);

            Product product9 = new Product();
            product9.setTitle("Tai nghe AirPods Pro");
            product9.setQuantity(15);
            product9.setDesc("Tai nghe không dây Apple AirPods Pro 2");
            product9.setPrice(6000000.0);
            product9.setImages("https://images.unsplash.com/photo-1606841837239-c5a1a4a07af7?w=400");
            product9.setUserid(user1.getId());
            productRepository.save(product9);

            Product product10 = new Product();
            product10.setTitle("Quần jean nam");
            product10.setQuantity(60);
            product10.setDesc("Quần jean nam chất liệu denim cao cấp");
            product10.setPrice(700000.0);
            product10.setImages("https://images.unsplash.com/photo-1542272604-787c3835535d?w=400");
            product10.setUserid(user2.getId());
            productRepository.save(product10);

            System.out.println("Đã khởi tạo dữ liệu mẫu thành công!");
            System.out.println("   - Categories: " + categoryRepository.count());
            System.out.println("   - Users: " + userRepository.count());
            System.out.println("   - Products: " + productRepository.count());
        };
    }
}

