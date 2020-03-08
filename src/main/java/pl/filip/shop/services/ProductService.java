package pl.filip.shop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.filip.shop.dto.ProductDto;
import pl.filip.shop.mapper.ProductMapper;
import pl.filip.shop.model.Category;
import pl.filip.shop.model.Product;
import pl.filip.shop.repositories.CategoryRepository;
import pl.filip.shop.repositories.ProductRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ProductMapper productMapper;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    public Product saveProduct(Product product) {
        Product copy = product;

        if (copy != null) {
            if (copy.getCreatedDate() != null) {
                copy.setCreatedDate(LocalDate.now());
            }
            return productRepository.save(copy);
        }
        throw new NullPointerException("Invalid product");
    }

    public boolean deleteProductById(Long id) {
        Optional<Product> objProd = productRepository.findById(id);

        if(objProd.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        throw new NullPointerException("Invalid id");
    }

    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    public List<Product> findProductsByName(String productName) {
        String upperCase = productName.substring(0, 1).toUpperCase() + productName.substring(1);
        String lowerCase = productName.substring(0, 1).toLowerCase() + productName.substring(1);
        List<Product> productList = productRepository.findAllByProductName(upperCase);
        productList.addAll(productRepository.findAllByProductName(lowerCase));

        if (productList.size() == 0) {
            List<Product> products = productRepository.findAll();
            return products;
        } else {
            return productList;
        }
    }

    public List<Product> findProductsByCategory(Long category_id) {
        Optional<Category> categoryObj = categoryRepository.findById(category_id);
        Category category = categoryObj.orElse(null);

        if(category == null) { throw new NullPointerException(); }
        List<Product> productList = productRepository.findAllByCategory(category);
        if (productList.isEmpty()) {
            List<Product> products = productRepository.findAll();
            return products;
        } else {
            return productList;
        }
    }

    public Product findById(Long id) {
        Optional<Product> object = productRepository.findById(id);
        return object.orElse(null);
    }

}
