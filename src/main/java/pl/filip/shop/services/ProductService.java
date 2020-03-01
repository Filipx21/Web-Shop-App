package pl.filip.shop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.filip.shop.model.Category;
import pl.filip.shop.model.Product;
import pl.filip.shop.repositories.ProductRepository;

import java.time.LocalDate;
import java.util.Collections;
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

    public Page<Product> findAllProduct(Pageable pageable) {
        List<Product> productList = productRepository.findAll();
        return fillPage(productList, pageable);
    }

    public Page<Product> findProductsByName(String productName, Pageable pageable) {
        String upperCase = productName.substring(0, 1).toUpperCase() + productName.substring(1);
        String lowerCase = productName.substring(0, 1).toLowerCase() + productName.substring(1);
        List<Product> productList = productRepository.findAllByProductName(upperCase);
        productList.addAll(productRepository.findAllByProductName(lowerCase));

        if (productList.size() == 0) {
            List<Product> products = productRepository.findAll();
            return fillPage(products, pageable);
        } else {
            return fillPage(productList, pageable);
        }
    }

    public Page<Product> findProductsByCategory(Category category, Pageable pageable) {
        List<Product> productList = productRepository.findAllByCategory(category);
        if (productList.isEmpty()) {
            List<Product> products = productRepository.findAll();
            return fillPage(products, pageable);
        } else {
            return fillPage(productList, pageable);
        }
    }


    private Page<Product> fillPage(List<Product> productList, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Product> products;

        if (productList.size() < startItem) {
            products = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, productList.size());
            products = productList.subList(startItem, toIndex);
        }
        return new PageImpl<>(products,
                PageRequest.of(currentPage, pageSize),
                productList.size());
    }

    public Product findById(Long id) {
        Optional<Product> object = productRepository.findById(id);
        return object.orElse(null);
    }

}
