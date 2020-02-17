package pl.filip.shop.services;

import org.springframework.stereotype.Service;
import pl.filip.shop.model.OrderUser;
import pl.filip.shop.model.Product;
import pl.filip.shop.model.ProductInOrder;
import pl.filip.shop.model.SysUser;
import pl.filip.shop.repositories.CartRepository;
import pl.filip.shop.repositories.OrderUserRepository;
import pl.filip.shop.repositories.ProductRepository;
import pl.filip.shop.repositories.SysUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BuyService {

    private SysUserRepository userRepository;
    private OrderUserRepository orderUserRepository;
    private ProductRepository productRepository;

    public BuyService(SysUserRepository userRepository,
                      OrderUserRepository orderUserRepository,
                      ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.orderUserRepository = orderUserRepository;
        this.productRepository = productRepository;
    }

    public OrderUser buyAllProductsFromCart(String user_email, List<ProductInOrder> products) {
        SysUser user = findUser(user_email);

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

    public List<OrderUser> findAllOrders(String user_email) {
        SysUser user = findUser(user_email);
        return orderUserRepository.findAllBySysUser(user);
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
