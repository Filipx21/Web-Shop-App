package pl.filip.shop.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.AssertionFailedError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import pl.filip.shop.dto.OrderUserDto;
import pl.filip.shop.model.Cart;
import pl.filip.shop.model.OrderUser;
import pl.filip.shop.model.ProductInOrder;
import pl.filip.shop.model.SysUser;
import pl.filip.shop.model.Product;
import pl.filip.shop.repositories.CartRepository;
import pl.filip.shop.repositories.OrderUserRepository;
import pl.filip.shop.repositories.ProductRepository;
import pl.filip.shop.repositories.SysUserRepository;
import pl.filip.shop.resource.DataTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuyServiceTest {

    @Mock
    SysUserRepository userRepository;

    @Mock
    OrderUserRepository orderUserRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    CartRepository cartRepository;

    @InjectMocks
    BuyService buyService;

    private DataTest dataTest;

    @BeforeEach
    void init() {
        dataTest = new DataTest();
    }

    @Test
    void shouldSendRequest() throws NullPointerException {
        OrderUser orderUser = dataTest.prepareOrderUser();

        when(orderUserRepository.findById(
                orderUser.getId())).thenReturn(Optional.of(orderUser));
        when(orderUserRepository.save(orderUser)).thenReturn(orderUser);
        OrderUser result = buyService.send(orderUser.getId());
        assertEquals(orderUser, result);
    }

    @Test
    void shouldNotSendRequest() throws NullPointerException {
        OrderUser orderUser = new OrderUser();

        orderUser.setId(1L);
        when(orderUserRepository.findById(
                orderUser.getId())).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () ->
            buyService.send(orderUser.getId())
        );
    }

    @Test
    void shouldThrowNullSendRequest() throws NullPointerException {
        OrderUser orderUser = dataTest.prepareOrderUser();

        orderUser.setId(1L);
        when(orderUserRepository.findById(
                orderUser.getId())).thenReturn(Optional.empty());
        assertThrows(NullPointerException.class, () ->
                buyService.send(orderUser.getId())
        );
    }

    @Test
    void shouldFindAllNotSent() {
        List<OrderUser> allOrderUser = List.of(
                dataTest.prepareOrderUser(),
                dataTest.prepareOrderUser(),
                dataTest.prepareOrderUser(),
                dataTest.prepareOrderUser(),
                dataTest.prepareOrderUser()
        );

        when(orderUserRepository.findAllByFinish(false))
                .thenReturn(allOrderUser);
        List<OrderUser> result = buyService.findAllNotSent();
        assertEquals(allOrderUser, result);
    }

    @Test
    void shouldNotFindAllNotSent() throws AssertionFailedError {
        List<OrderUser> allOrderUser = new ArrayList<>();

        when(orderUserRepository.findAllByFinish(false))
                .thenReturn(allOrderUser);
        List<OrderUser> result =  buyService.findAllNotSent();
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindAllSent() {
        List<OrderUser> allOrderUser = List.of(
                dataTest.prepareOrderUser(),
                dataTest.prepareOrderUser(),
                dataTest.prepareOrderUser(),
                dataTest.prepareOrderUser(),
                dataTest.prepareOrderUser()
        );
        allOrderUser.forEach(o -> o.setFinish(true));

        when(orderUserRepository.findAllByFinish(true))
                .thenReturn(allOrderUser);
        List<OrderUser> result = buyService.findAllSent();
        assertEquals(allOrderUser, result);
    }

    @Test
    void shouldNotFindAllSent() {
        List<OrderUser> allOrderUser = new ArrayList<>();

        when(orderUserRepository.findAllByFinish(true))
                .thenReturn(allOrderUser);
        List<OrderUser> result =  buyService.findAllSent();
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldNullCartBuyAllProductsFromCart() {
        SysUser user = dataTest.prepareUser();
        List<Cart> carts = new ArrayList<>();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cartRepository.findAllBySysUserAndInUse(user, true)).thenReturn(carts);

        OrderUser result = buyService.buyAllProductsFromCart(user.getEmail());

        assertNull(result);
    }

    @Test
    void shouldThrowIndexOutBuyAllProductsFromCart() {
        SysUser user = dataTest.prepareUser();
        List<Cart> carts = List.of(
                dataTest.prepareCart(),
                dataTest.prepareCart(),
                dataTest.prepareCart()
        );

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cartRepository.findAllBySysUserAndInUse(user, true)).thenReturn(carts);

        assertThrows(IndexOutOfBoundsException.class, () ->
                buyService.buyAllProductsFromCart(user.getEmail())
        );
    }

    @Test
    void shouldBuyAllProductsFromCart() {
        SysUser user = dataTest.prepareUser();
        List<Cart> carts = List.of(dataTest.prepareCart());
        Cart cart = carts.get(0);
        OrderUser order = dataTest.prepareOrderUser();
        order.setId(null);
        order.setSysUser(user);
        order.setProductInOrders(cart.getProducts());
        order.setDone(true);
        order.setFinish(false);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cartRepository.findAllBySysUserAndInUse(user, true)).thenReturn(carts);
        when(cartRepository.save(cart)).thenReturn(cart);
        when(orderUserRepository.save(order)).thenReturn(order);

        OrderUser result = buyService.buyAllProductsFromCart(user.getEmail());

        assertEquals(order, result);
    }

    @Test
    void shouldBuyProduct() {
        Product product = dataTest.prepareProduct();
        SysUser user = dataTest.prepareUser();
        List<ProductInOrder> products = List.of(new ProductInOrder(product));
        OrderUser order = dataTest.prepareOrderUser();
        order.setId(null);
        order.setSysUser(user);
        order.setProductInOrders(products);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(orderUserRepository.save(order)).thenReturn(order);

        OrderUser result = buyService.buyProduct(user.getEmail(), product.getId());

        assertEquals(order, result);
    }

    @Test
    void shouldNotBuyProduct() {
        Product product = dataTest.prepareProduct();
        SysUser user = dataTest.prepareUser();
        List<ProductInOrder> products = List.of(new ProductInOrder(product));
        OrderUser order = dataTest.prepareOrderUser();
        order.setId(null);
        order.setSysUser(user);
        order.setProductInOrders(products);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(orderUserRepository.save(order)).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () ->
                buyService.buyProduct(user.getEmail(), product.getId())
        );
    }

    @Test
    void shouldThrowNullForProductBuyProduct() {
        Product product = dataTest.prepareProduct();

        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () ->
                buyService.buyProduct("test@gmail.com", product.getId())
        );
    }

    @Test
    void shouldThrowNullForUserBuyProduct() {
        Product product = dataTest.prepareProduct();
        SysUser user = dataTest.prepareUser();

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () ->
                buyService.buyProduct(user.getEmail(), product.getId())
        );
    }

    @Test
    void shouldFindAllOrders() {
        SysUser user = dataTest.prepareUser();
        List<OrderUser> orders = List.of(
                dataTest.prepareOrderUser(),
                dataTest.prepareOrderUser(),
                dataTest.prepareOrderUser(),
                dataTest.prepareOrderUser()
        );
        orders.forEach(x -> x.setSysUser(user));

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(orderUserRepository.findAllBySysUser(user)).thenReturn(orders);

        List<OrderUser> result = buyService.findAllOrders(user.getEmail());

        assertEquals(orders, result);
    }

    @Test
    void shouldNotFindAllOrders() {
        SysUser user = dataTest.prepareUser();
        List<OrderUser> orders = List.of();
        orders.stream()
                .sorted(Collections.reverseOrder())
                .forEach(x -> x.setSysUser(user));

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(orderUserRepository.findAllBySysUser(user)).thenReturn(orders);

        List<OrderUser> result = buyService.findAllOrders(user.getEmail());

        assertEquals(orders, result);
    }

    private Page<OrderUserDto> fillPage(Pageable pageable, List<OrderUserDto> ordersList) {
        var pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = pageSize * currentPage;
        List<OrderUserDto> orders;
        if (ordersList.size() < startItem) {
            orders = Collections.emptyList();
        } else {
            int index = Math.min(startItem + pageSize,
                    ordersList.size());
            orders = ordersList.subList(startItem, index);
        }
        return new PageImpl<>(orders,
                PageRequest.of(currentPage, pageSize),
                ordersList.size());
    }

}