package pl.filip.tosql.services;

import pl.filip.tosql.model.Product;
import pl.filip.tosql.repositories.CartRepository;
import pl.filip.tosql.repositories.ProductInOrderRepository;
import pl.filip.tosql.repositories.ProductRepository;

public class CartService {

    private ProductRepository productRepository;
    private ProductInOrderRepository productInOrderRepository;
    private CartRepository cartRepository;

    public CartService(ProductRepository productRepository,
        ProductInOrderRepository productInOrderRepository,
        CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.productInOrderRepository = productInOrderRepository;
        this.cartRepository = cartRepository;
    }

    public void addProduct(Product product) {
    }

    public void increaseQuantity(Product product) {
    }

    public void decreaseQuantity(Product product) {
    }

    public void removeById(Long id) {
    }

    public void buy() {
    }

    public void deleteAll() {
    }

}
