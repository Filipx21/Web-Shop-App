package pl.filip.shop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
        if(objOrder.isPresent()) {
            OrderUser order = objOrder.get();
            order.setFinish(true);
            orderUserRepository.save(order);
        }
        throw new NullPointerException("Nie ma takiego zlecenia");
    }

    public OrderUser buyAllProductsFromCart(String user_email) {
        SysUser user = findUser(user_email);
        List<Cart> carts = cartRepository.findAllBySysUserAndInUse(user, true);
        if(carts.isEmpty()){
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

    public OrderUser buyProduct(String user_email, Long productId){
        Optional<Product> productObj = productRepository.findById(productId);
        ProductInOrder product = null;
        if(productObj.isPresent()){
            product = new ProductInOrder(productObj.get());
        } else {
            throw new NullPointerException("Nie ma takieggo produktu");
        }
        SysUser user = findUser(user_email);
        List<ProductInOrder> products = new ArrayList<>();
        products.add(product);
        return saveOrder(user, products);
    }

    public Page<OrderUser> findAllOrders(String user_email, Pageable pageable) {
        SysUser user = findUser(user_email);
        List<OrderUser> orders = orderUserRepository.findAllBySysUser(user);
        Collections.reverse(orders);
        return fillPage(orders, pageable);
    }

    private Page<OrderUser> fillPage(List<OrderUser> ordersList, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<OrderUser> orders;
        if(ordersList.size() < startItem){
            orders = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, ordersList.size());
            orders = ordersList.subList(startItem, toIndex);
        }
        return new PageImpl<>(orders,
                PageRequest.of(currentPage, pageSize),
                ordersList.size());
    }

    private OrderUser saveOrder(SysUser user, List<ProductInOrder> products){
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
