package pl.filip.tosql.services;


import org.springframework.stereotype.Service;
import pl.filip.tosql.model.Product;
import pl.filip.tosql.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService{

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Product findProductById(Long id){
        Optional<Product> object = productRepository.findById(id);
        if(object.isPresent()){
            return object.get();
        }
        return null;
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public Product editProduct(Product product){
        return null;
    }

    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }

}
