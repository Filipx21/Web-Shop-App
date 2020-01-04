package pl.filip.tosql.services;

import org.springframework.stereotype.Service;
import pl.filip.tosql.model.Cart;
import pl.filip.tosql.model.Product;
import pl.filip.tosql.model.ProductInOrder;
import pl.filip.tosql.model.User;
import pl.filip.tosql.repositories.CartRepository;
import pl.filip.tosql.repositories.ProductRepository;
import pl.filip.tosql.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Cart addToCart(Long id, String username) {
        Optional<User> userObj = userRepository.findByUsername(username);
        Optional<Product> productObj = productRepository.findById(id);
        User user;
        Product product;

        if (!userObj.isPresent() || !productObj.isPresent()) {
            throw new NullPointerException("Object doesn't exist");
        } else {
            user = userObj.get();
            product = productObj.get();
        }
        List<Cart> carts = cartRepository.findAllByUserAndInUse(user,true);
        return cartRepository.save(checkCart(carts, user, product));
    }

    private Cart checkCart(List<Cart> carts, User user, Product product) {
        Cart cart;
        if(carts.size() > 1) {
            throw new NullPointerException("Occured fatal error");
        } else if (carts.size() < 1) {
            List<ProductInOrder> productsInOrder = new ArrayList<>();
            productsInOrder.add(new ProductInOrder(product));
            cart = new Cart();
            cart.setProducts(productsInOrder);
            cart.setUser(user);
            cart.setInUse(true);
            return cart;
        } else {
            cart = carts.get(0);
            List<ProductInOrder> productsInOrder = cart.getProducts();
            if(productsInOrder.contains(new ProductInOrder(product))){
                return null;
            }
            productsInOrder.add(new ProductInOrder(product));
            cart.setProducts(productsInOrder);
            return cart;
        }
    }

    public List<ProductInOrder> productsInCart(String username) {
        Optional<User> userObj = userRepository.findByUsername(username);
        List<Cart> objCart;
        if (!userObj.isPresent() ) {
            throw new NullPointerException("Object doesn't exist");
        } else {
            objCart = cartRepository.findAllByUserAndInUse(userObj.get(),true);
        }
        if(objCart.size() < 1) {
            return null;
        } else if (objCart.size() == 1){
            return objCart.get(0).getProducts();

        }
        throw new NullPointerException();
    }

    public Cart deleteProductFromCart(Long id, String username) {
        Optional<User> userObj = userRepository.findByUsername(username);
        User user;

        if (!userObj.isPresent() ) {
            throw new NullPointerException("Object doesn't exist");
        } else {
            user = userObj.get();
        }
        List<Cart> carts = cartRepository.findAllByUserAndInUse(user, true);
        if(carts.size() > 1) {
            throw new NullPointerException();
        }
        Cart cart = carts.get(0);
        List<ProductInOrder> productsInOrder = cart.getProducts();
        for (int i = 0; i < productsInOrder.size(); i++){
            if(productsInOrder.get(i).getId().equals(id)){
                productsInOrder.remove(i);
            }
        }
        cart.setProducts(productsInOrder);
        return cartRepository.save(cart);
    }

    public Cart clean(String username) {
        Optional<User> userObj = userRepository.findByUsername(username);
        if(userObj.isPresent()){
            List<Cart> carts = cartRepository.findAllByUserAndInUse(userObj.get(), true);
            if(carts.size() > 1){
                throw new NullPointerException();
            }
            Cart cart = carts.get(0);
            cart.setInUse(false);
            return cartRepository.save(cart);
        }
        throw new NullPointerException();
    }

}
