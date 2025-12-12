package com.giap.baitap08.graphql;

import com.giap.baitap08.entity.Product;
import com.giap.baitap08.entity.User;
import com.giap.baitap08.repository.ProductRepository;
import com.giap.baitap08.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductResolver {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @QueryMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @QueryMapping
    public List<Product> getProductsSortedByPrice() {
        return productRepository.findAllByOrderByPriceAsc();
    }

    @QueryMapping
    public Product getProductById(@Argument Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public List<Product> getProductsByCategoryId(@Argument Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @MutationMapping
    public Product createProduct(@Argument String title, @Argument Integer quantity,
                                @Argument String desc, @Argument Double price,
                                @Argument Long userid) {
        if (!userRepository.existsById(userid)) {
            throw new RuntimeException("User not found");
        }
        Product product = new Product();
        product.setTitle(title);
        product.setQuantity(quantity);
        product.setDesc(desc);
        product.setPrice(price);
        product.setUserid(userid);
        return productRepository.save(product);
    }

    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument String title,
                                @Argument Integer quantity, @Argument String desc,
                                @Argument Double price, @Argument Long userid) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (title != null) product.setTitle(title);
        if (quantity != null) product.setQuantity(quantity);
        if (desc != null) product.setDesc(desc);
        if (price != null) product.setPrice(price);
        if (userid != null) {
            if (!userRepository.existsById(userid)) {
                throw new RuntimeException("User not found");
            }
            product.setUserid(userid);
        }
        return productRepository.save(product);
    }

    @MutationMapping
    public Boolean deleteProduct(@Argument Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

