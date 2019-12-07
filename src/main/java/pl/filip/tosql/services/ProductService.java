package pl.filip.tosql.services;

import org.springframework.stereotype.Service;
import pl.filip.tosql.model.Product;
import pl.filip.tosql.repositories.ProductRepository;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void saveProduct(Product product) {
    }

    public void deleteProductById(Long id) {
    }

    public void findAllProduct() {
    }

    public void findById(Long id) {
    }

    public void editProduct(Product product) {
    }

}
