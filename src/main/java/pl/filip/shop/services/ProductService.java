package pl.filip.shop.services;

import org.springframework.stereotype.Service;
import pl.filip.shop.model.Product;
import pl.filip.shop.repositories.ProductRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        Product copy = product;
        if (copy != null) {
            if (copy.getCreate() != null) {
                copy.setCreate(LocalDate.now());
            }
            return productRepository.save(copy);
        }
        return null;
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        Optional<Product> object = productRepository.findById(id);
        return object.orElse(null);
    }

    public List<Product> findProductsByName(String productName) {
        String upperCase = productName.substring(0, 1).toUpperCase() + productName.substring(1);
        String lowerCase = productName.substring(0, 1).toLowerCase() + productName.substring(1);
        List<Product> products = productRepository.findAllByProductName(upperCase);
        products.addAll(productRepository.findAllByProductName(lowerCase));
        if (products.isEmpty()) {
            return null;
        } else {
            return products;
        }
    }

}
