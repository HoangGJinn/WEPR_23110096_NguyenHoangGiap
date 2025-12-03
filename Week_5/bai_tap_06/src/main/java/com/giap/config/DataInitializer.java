package com.giap.config;

import com.giap.entity.Category;
import com.giap.entity.User;
import com.giap.entity.Video;
import com.giap.service.CategoryService;
import com.giap.service.UserService;
import com.giap.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private VideoService videoService;

    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra xem đã có dữ liệu chưa
        if (userService.findAll().isEmpty()) {
            initializeData();
        }
    }

    private void initializeData() {
        System.out.println("Đang tạo dữ liệu mẫu...");

        // Tạo Users
        List<User> users = createUsers();
        System.out.println("Đã tạo " + users.size() + " users");

        // Tạo Categories
        List<Category> categories = createCategories(users);
        System.out.println("Đã tạo " + categories.size() + " categories");

        // Tạo Videos
        List<Video> videos = createVideos(categories);
        System.out.println("Đã tạo " + videos.size() + " videos");

        System.out.println("Hoàn thành tạo dữ liệu mẫu!");
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();

        // User 1 - Admin
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin123");
        admin.setFullname("Nguyễn Văn Admin");
        admin.setEmail("admin@example.com");
        admin.setPhone("0901234567");
        admin.setAdmin(true);
        admin.setActive(true);
        admin.setImages("https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=150&h=150&fit=crop&crop=face");
        users.add(userService.save(admin));

        // User 2
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("user123");
        user1.setFullname("Trần Thị Hoa");
        user1.setEmail("user1@example.com");
        user1.setPhone("0902345678");
        user1.setAdmin(false);
        user1.setActive(true);
        user1.setImages("https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150&h=150&fit=crop&crop=face");
        users.add(userService.save(user1));


        return users;
    }

    private List<Category> createCategories(List<User> users) {
        List<Category> categories = new ArrayList<>();

        if (users.isEmpty()) return categories;

        User admin = users.get(0);
        User user1 = users.size() > 1 ? users.get(1) : admin;

        // Category 1 - Phim hành động
        Category cat1 = new Category();
        cat1.setCategoryName("Phim Hành Động");
        cat1.setCategoryCode("ACTION");
        cat1.setStatus(true);
        cat1.setUser(admin);
        cat1.setImages("https://images.unsplash.com/photo-1551524164-6cf77f5e1e48?w=400&h=300&fit=crop");
        categories.add(categoryService.save(cat1));

        // Category 2 - Phim hài
        Category cat2 = new Category();
        cat2.setCategoryName("Phim Hài");
        cat2.setCategoryCode("COMEDY");
        cat2.setStatus(true);
        cat2.setUser(admin);
        cat2.setImages("https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=400&h=300&fit=crop");
        categories.add(categoryService.save(cat2));

        // Category 3 - Phim tình cảm
        Category cat3 = new Category();
        cat3.setCategoryName("Phim Tình Cảm");
        cat3.setCategoryCode("ROMANCE");
        cat3.setStatus(true);
        cat3.setUser(user1);
        cat3.setImages("https://images.unsplash.com/photo-1518568814500-bf0f65dbe1e1?w=400&h=300&fit=crop");
        categories.add(categoryService.save(cat3));

        // Category 4 - Phim kinh dị
        Category cat4 = new Category();
        cat4.setCategoryName("Phim Kinh Dị");
        cat4.setCategoryCode("HORROR");
        cat4.setStatus(true);
        cat4.setUser(admin);
        cat4.setImages("https://images.unsplash.com/photo-1518709268805-4e9042af2176?w=400&h=300&fit=crop");
        categories.add(categoryService.save(cat4));

        // Category 5 - Phim khoa học viễn tưởng
        Category cat5 = new Category();
        cat5.setCategoryName("Phim Khoa Học Viễn Tưởng");
        cat5.setCategoryCode("SCI-FI");
        cat5.setStatus(true);
        cat5.setUser(user1);
        cat5.setImages("https://images.unsplash.com/photo-1446776653964-20c1d3a81b06?w=400&h=300&fit=crop");
        categories.add(categoryService.save(cat5));

        // Category 6 - Phim tài liệu
        Category cat6 = new Category();
        cat6.setCategoryName("Phim Tài Liệu");
        cat6.setCategoryCode("DOCUMENTARY");
        cat6.setStatus(false);
        cat6.setUser(admin);
        cat6.setImages("https://images.unsplash.com/photo-1492691527719-9d1e07e534b4?w=400&h=300&fit=crop");
        categories.add(categoryService.save(cat6));

        return categories;
    }

    private List<Video> createVideos(List<Category> categories) {
        List<Video> videos = new ArrayList<>();

        if (categories.isEmpty()) return videos;

        Category action = categories.get(0);
        Category comedy = categories.size() > 1 ? categories.get(1) : action;
        Category romance = categories.size() > 2 ? categories.get(2) : action;
        Category horror = categories.size() > 3 ? categories.get(3) : action;
        Category scifi = categories.size() > 4 ? categories.get(4) : action;

        // Video 1 - Hành động
        Video v1 = new Video();
        v1.setTitle("Fast & Furious 10");
        v1.setPoster("https://images.unsplash.com/photo-1551524164-6cf77f5e1e48?w=600&h=900&fit=crop");
        v1.setViews("1500000");
        v1.setDescription("Phim hành động đầy kịch tính với những cảnh đua xe tốc độ cao và các pha nguy hiểm.");
        v1.setActive(true);
        v1.setCategory(action);
        videos.add(videoService.save(v1));

        // Video 2 - Hành động
        Video v2 = new Video();
        v2.setTitle("John Wick 4");
        v2.setPoster("https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=600&h=900&fit=crop");
        v2.setViews("2300000");
        v2.setDescription("Cuộc chiến cuối cùng của sát thủ huyền thoại John Wick.");
        v2.setActive(true);
        v2.setCategory(action);
        videos.add(videoService.save(v2));

        // Video 3 - Hài
        Video v3 = new Video();
        v3.setTitle("Gặp Nhau Cuối Năm");
        v3.setPoster("https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=600&h=900&fit=crop");
        v3.setViews("850000");
        v3.setDescription("Bộ phim hài Tết với những tình huống dở khóc dở cười của một gia đình.");
        v3.setActive(true);
        v3.setCategory(comedy);
        videos.add(videoService.save(v3));

        // Video 4 - Hài
        Video v4 = new Video();
        v4.setTitle("Cô Ba Sài Gòn");
        v4.setPoster("https://images.unsplash.com/photo-1485846234645-a62644f84728?w=600&h=900&fit=crop");
        v4.setViews("1200000");
        v4.setDescription("Câu chuyện hài hước về cuộc sống của một cô gái Sài Gòn.");
        v4.setActive(true);
        v4.setCategory(comedy);
        videos.add(videoService.save(v4));

        // Video 5 - Tình cảm
        Video v5 = new Video();
        v5.setTitle("Em Chưa 18");
        v5.setPoster("https://images.unsplash.com/photo-1518568814500-bf0f65dbe1e1?w=600&h=900&fit=crop");
        v5.setViews("2100000");
        v5.setDescription("Bộ phim tình cảm tuổi học trò đầy cảm động và ngọt ngào.");
        v5.setActive(true);
        v5.setCategory(romance);
        videos.add(videoService.save(v5));

        // Video 6 - Tình cảm
        Video v6 = new Video();
        v6.setTitle("Cô Gái Đến Từ Hôm Qua");
        v6.setPoster("https://images.unsplash.com/photo-1517841905240-472988babdf9?w=600&h=900&fit=crop");
        v6.setViews("980000");
        v6.setDescription("Câu chuyện tình yêu lãng mạn giữa hai thế hệ.");
        v6.setActive(true);
        v6.setCategory(romance);
        videos.add(videoService.save(v6));

        // Video 7 - Kinh dị
        Video v7 = new Video();
        v7.setTitle("The Conjuring 3");
        v7.setPoster("https://images.unsplash.com/photo-1518709268805-4e9042af2176?w=600&h=900&fit=crop");
        v7.setViews("1800000");
        v7.setDescription("Phim kinh dị về những vụ án ma quái có thật.");
        v7.setActive(true);
        v7.setCategory(horror);
        videos.add(videoService.save(v7));

        // Video 8 - Kinh dị
        Video v8 = new Video();
        v8.setTitle("Annabelle");
        v8.setPoster("https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=600&h=900&fit=crop");
        v8.setViews("1650000");
        v8.setDescription("Câu chuyện về con búp bê ma quái Annabelle.");
        v8.setActive(true);
        v8.setCategory(horror);
        videos.add(videoService.save(v8));

        // Video 9 - Khoa học viễn tưởng
        Video v9 = new Video();
        v9.setTitle("Interstellar");
        v9.setPoster("https://images.unsplash.com/photo-1446776653964-20c1d3a81b06?w=600&h=900&fit=crop");
        v9.setViews("3200000");
        v9.setDescription("Cuộc hành trình không gian để cứu nhân loại.");
        v9.setActive(true);
        v9.setCategory(scifi);
        videos.add(videoService.save(v9));

        return videos;
    }
}

