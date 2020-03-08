package pl.filip.shop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.filip.shop.dto.OrderUserDto;
import pl.filip.shop.mapper.OrderUserMapper;
import pl.filip.shop.model.*;
import pl.filip.shop.repositories.CartRepository;
import pl.filip.shop.repositories.OrderUserRepository;
import pl.filip.shop.repositories.ProductRepository;
import pl.filip.shop.repositories.SysUserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BuyService {

    private SysUserRepository userRepository;
    private OrderUserRepository orderUserRepository;
    private ProductRepository productRepository;
    private CartRepository cartRepository;

    public BuyService(SysUserRepository userRepository,
                      OrderUserRepository orderUserRepository,
                      ProductRepository productRepository,
                      CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.orderUserRepository = orderUserRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public OrderUser send(Long id) {
        Optional<OrderUser> objOrder = orderUserRepository.findById(id);
        if (objOrder.isPresent()) {
            OrderUser order = objOrder.get();
            order.setFinish(!order.isFinish());
            return orderUserRepository.save(order);
        } else {
            throw new NullPointerException("Nie ma takiego zlecenia");
        }
    }

    public List<OrderUser> findAllNotSent() {
        return orderUserRepository.findAllByFinish(false);
    }

    public List<OrderUser> findAllSent() {
        return orderUserRepository.findAllByFinish(true);
    }

    public OrderUser buyAllProductsFromCart(String user_email) {
        SysUser user = findUser(user_email);
        List<Cart> carts = cartRepository.findAllBySysUserAndInUse(user, true);
        if (carts.isEmpty()) {
            return null;
        }
        if (carts.size() != 1) {
            throw new IndexOutOfBoundsException(LocalDate.now()
                    + ": Error occurred while searching the cart. Too many carts");
        }
        Cart cart = carts.get(0);
        List<ProductInOrder> products = new ArrayList<>(cart.getProducts());
        cart.setInUse(false);
        cartRepository.save(cart);
        return saveOrder(user, products);
    }

    public OrderUser buyProduct(String user_email, Long productId) {
        Optional<Product> productObj = productRepository.findById(productId);
        ProductInOrder product;
        if (productObj.isPresent()) {
            product = new ProductInOrder(productObj.get());
        } else {
            throw new NullPointerException("Nie ma takiego produktu");
        }
        SysUser user = findUser(user_email);
        if(user == null) {
            throw new NullPointerException("Nie ma takiego uzytkownika");
        }
        List<ProductInOrder> products = new ArrayList<>();
        products.add(product);
        return saveOrder(user, products);
    }

    public List<OrderUser> findAllOrders(String user_email) {
        SysUser user = findUser(user_email);
        return orderUserRepository.findAllBySysUser(user);
    }

    private OrderUser saveOrder(SysUser user, List<ProductInOrder> products) {
        OrderUser order = new OrderUser();
        order.setSysUser(user);
        order.setProductInOrders(products);
        order.setDone(true);
        order.setFinish(false);
        return orderUserRepository.save(order);
    }

    private SysUser findUser(String user_email) {
        Optional<SysUser> userObj = userRepository.findByEmail(user_email);
        return userObj.orElse(null);
    }


}
