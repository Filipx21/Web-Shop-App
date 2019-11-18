package pl.filip.tosql.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.filip.tosql.model.Product;
import pl.filip.tosql.services.ProductService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class ProductController {

    private ProductService productService;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productServiceImpl) {
        this.productService = productServiceImpl;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> products = productService.findAllProducts();
        if(products != null){
            return ResponseEntity.ok().body(products);
        }
        log.error("Not found products");
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id){
        Product product = productService.findProductById(id);
        if(product != null){
            return ResponseEntity.ok().body(product);
        }
        log.error("Not found product");
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/product/")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        if(product != null) {
            productService.saveProduct(product);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(product.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(product);
        }
        log.warn("Received product is null");
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/product/")
    public ResponseEntity<Product> editProduct(@RequestBody Product product){
        if(product != null){
            Product edited = productService.editProduct(product);
            if(edited != null){
                return ResponseEntity.ok().body(edited);
            }
            log.error("Not found product");
            return ResponseEntity.notFound().build();
        }
        log.warn("Received product is null");
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable("id") Long id){
//        Product deleted = productService.deleteProductById(id);
//        if(deleted != null){
//            return ResponseEntity.noContent().build();
//        }
//        log.error("Not found product");
        return ResponseEntity.notFound().build();
    }

}
