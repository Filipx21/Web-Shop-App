package pl.filip.shop.services;

import org.springframework.stereotype.Service;
import pl.filip.shop.model.Category;
import pl.filip.shop.model.Product;
import pl.filip.shop.repositories.CategoryRepository;
import pl.filip.shop.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository,
                           ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        Optional<Category> objCategory = categoryRepository.findById(id);
        return objCategory.orElse(null);
    }

    public Category save(Category category) {
        if (category == null || category.getCategory().isEmpty()) {
            throw new NullPointerException("Category is null");
        }
        return categoryRepository.save(category);
    }

    public Category delete(Long id) {
        Optional<Category> catObj = categoryRepository.findById(id);
        if (catObj.isPresent()) {
            Category category = catObj.get();
            List<Product> products = productRepository.findAllByCategory(category);
            if (products.isEmpty()) {
                categoryRepository.deleteById(id);
                return category;
            } else {
                return null;
            }
        }
        throw new NullPointerException("Invalid category");
    }

}
