package pl.filip.shop.services;

import org.springframework.stereotype.Service;
import pl.filip.shop.model.Cart;
import pl.filip.shop.model.Product;
import pl.filip.shop.model.ProductInOrder;
import pl.filip.shop.model.User;
import pl.filip.shop.repositories.CartRepository;
import pl.filip.shop.repositories.ProductRepository;
import pl.filip.shop.repositories.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository,
                       UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<ProductInOrder> productsInCart(String user_email) {
        try {
            User user = findUserByEmail(user_email);
            Cart cart = manageCart(user);
            return cart.getProducts();
        } catch (NullPointerException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    public Cart addProductToCart(Long product_id, String user_email) {
        try {
            User user = findUserByEmail(user_email);
            Product product = findProductById(product_id);
            Cart cart = manageCart(user);
            List<ProductInOrder> products = cart.getProducts();
            products.add(new ProductInOrder(product));
            cart.setProducts(products);
            return cartRepository.save(cart);
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    public Cart deleteProductFromCart(Long id, String user_email) {
        try {
            User user = findUserByEmail(user_email);
            Cart cart = manageCart(user);
            List<ProductInOrder> products = cart.getProducts()
                    .stream()
                    .filter(product -> !product.getId().equals(id))
                    .collect(Collectors.toList());
            cart.setProducts(products);
            return cartRepository.save(cart);
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    public Cart clean(String user_email) {
        try {
            User user = findUserByEmail(user_email);
            Cart cart = manageCart(user);
            cart.setInUse(false);
            return cartRepository.save(cart);
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    private Cart manageCart(User user) throws IndexOutOfBoundsException {
        List<Cart> carts = cartRepository.findAllByUserAndInUse(user, true);
        if (carts.size() > 1) {
            throw new IndexOutOfBoundsException(LocalDate.now()
                    + ": Error occurred while searching the cart. Too many carts");
        } else if (carts.size() == 1) {
            return carts.get(0);
        }
        return new Cart(new ArrayList<>(), user, true);
    }

    private User findUserByEmail(String user_email) throws NullPointerException {
        Optional<User> optionalUser = userRepository.findByEmail(user_email);
        if (!optionalUser.isPresent()) {
            throw new NullPointerException(LocalDate.now()
                    + ": This user [" + user_email + "] doesn't exist.");
        } else {
            return optionalUser.get();
        }
    }

    private Product findProductById(Long product_id) throws NullPointerException {
        Optional<Product> optionalProduct = productRepository.findById(product_id);
        if (!optionalProduct.isPresent()) {
            throw new NullPointerException(LocalDate.now()
                    + ": Product with id [" + product_id + "] doesn't exist.");
        } else {
            return optionalProduct.get();
        }
    }
}
