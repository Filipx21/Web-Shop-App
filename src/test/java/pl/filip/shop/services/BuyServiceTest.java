package pl.filip.shop.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.AssertionFailedError;
import pl.filip.shop.model.*;
import pl.filip.shop.repositories.CartRepository;
import pl.filip.shop.repositories.OrderUserRepository;
import pl.filip.shop.repositories.ProductRepository;
import pl.filip.shop.repositories.SysUserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    @Test
    void shouldSendRequest() throws NullPointerException {
        OrderUser orderUser = prepareOrderUser();

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
    void shouldFindAllNotSent() {
        List<OrderUser> allOrderUser = List.of(
            prepareOrderUser(),
            prepareOrderUser(),
            prepareOrderUser(),
            prepareOrderUser(),
            prepareOrderUser()
        );

        when(orderUserRepository.findAllByFinish(false))
                .thenReturn(allOrderUser);
        List<OrderUser> result =  buyService.findAllNotSent();
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
                prepareOrderUser(),
                prepareOrderUser(),
                prepareOrderUser(),
                prepareOrderUser(),
                prepareOrderUser()
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
    void shouldBuyAllProductsFromCart() {
        SysUser user = prepareUser();
        Cart cart = prepareCart();
        cart.setSysUser(user);
        cart.setInUse(true);
        List<ProductInOrder> allProducts = prepareProductInOrder();
        List<Cart> carts = List.of(cart);
        OrderUser order = prepareOrderUser();
        order.setSysUser(user);
        order.setProductInOrders(allProducts);
        order.setDone(true);
        order.setFinish(false);



        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cartRepository.findAllBySysUserAndInUse(user, true)).thenReturn(carts);
        when(cartRepository.save(cart)).thenReturn(cart);
        when(orderUserRepository.save(order)).thenReturn(order);

        System.out.println(order);



        assertEquals(order.getSysUser().getId(), buyService.buyAllProductsFromCart(user.getEmail()));
    }

    /*
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

        private OrderUser saveOrder(SysUser user, List<ProductInOrder> products) {
        OrderUser order = new OrderUser();
        order.setSysUser(user);
        order.setProductInOrders(products);
        order.setDone(true);
        order.setFinish(false);
        return orderUserRepository.save(order);
    }
    }
     */

    @Test
    void shouldNotBuyAllProductsFromCart() {
    }

    @Test
    void shouldBuyProduct() {
    }

    @Test
    void shouldNotBuyProduct() {
    }

    @Test
    void shouldFindAllOrders() {
    }

    @Test
    void shouldNotFindAllOrders() {
    }

    private Cart prepareCart() {
        Random random = new Random();
        Cart cart = new Cart();
        cart.setId(Math.abs(random.nextLong() + 100));
        cart.setProducts(prepareProductInOrder());
        cart.setSysUser(prepareUser());
        cart.setInUse(false);
        return cart;
    }

    private OrderUser prepareOrderUser() {
        Random random = new Random();
        OrderUser orderUser = new OrderUser();
        orderUser.setId(Math.abs(random.nextLong() + 100));
        orderUser.setProductInOrders(prepareProductInOrder());
        orderUser.setSysUser(prepareUser());
        orderUser.setDone(true);
        orderUser.setFinish(false);
        return orderUser;
    }

    private List<ProductInOrder> prepareProductInOrder() {
        List<Product> allProducts = List.of(
                prepareProduct(),
                prepareProduct(),
                prepareProduct(),
                prepareProduct(),
                prepareProduct(),
                prepareProduct(),
                prepareProduct(),
                prepareProduct()
        );
        return allProducts
                .stream()
                .map(ProductInOrder::new)
                .collect(Collectors.toList());
    }

    private Product prepareProduct() {
        Random random = new Random();
        String[] names = new String[]{"Rower", "Rolki", "Myszka",
                "Garnek", "Kawa", "Woda", "Sol", "Lampa", "Posciel"};
        Product product = new Product();
        product.setId(random.nextLong() + 100);
        product.setProductName(names[random.nextInt(8)]);
        product.setDescript(names[random.nextInt(8)]);
        product.setQuantity(random.nextInt(100) + 1);
        product.setCost(new BigDecimal(random.nextDouble() * 100 + 1));
        product.setProducer(preapreProducer());
        product.setCreatedDate(LocalDate.now());
        product.setCategory(prepareCategory());
        return product;
    }

    private SysUser prepareUser() {
        SysUser user = new SysUser();
        Random random = new Random();
        String[] lastNames = new String[] {"Kwadrat","Giwera","Maven","Konik","Ura"};
        String[] firstNames = new String[] {"Eustachy","Gienia","Ewelina","Kornel","Bartek"};
        String[] addresses = new String[] {"Kornicza 2","Ernesta 33","Mavena 3","Konika 66","Orki 99"};
        String[] cities = new String[] {"Wroclaw","Warszawa","Gdansk","Gdynia","Zakopane"};
        String[] emails = new String[] {"test1@gmail.com","test2@gmail.com","test3@gmail.com","test4@gmail.com","test5@gmail.com"};
        Collection roles = new ArrayList();
        roles.add(prepareRole());
        user.setId(Long.valueOf(random.nextInt(1000)));
        user.setFirstName(firstNames[random.nextInt(5)]);
        user.setLastName(lastNames[random.nextInt(5)]);
        user.setAddress(addresses[random.nextInt(5)]);
        user.setPostCode("22-321");
        user.setCity(cities[random.nextInt(5)]);
        user.setEmail(emails[random.nextInt(5)]);
        user.setPassword("123");
        user.setInUse(true);
        user.setRoles(roles);
        return user;
    }

    private Producer preapreProducer() {
        Producer producer = new Producer();
        Random random = new Random();
        String[] names = new String[]{"PCC", "Wapniaki", "JA", "Mavos",
                "Intermodal", "DCOM", "Oldb", "Michalki", "Test"};
        String[] addresses = new String[]{"Ruska 55", "Kamien 2", "Wroclaw 44",
                "Warszawa 555", "Wilcza 43", "sfdfsd 34", "Testowa 33",
                "Testowa 22", "Testowa 13"};
        producer.setId(random.nextLong() + 100);
        producer.setAddress(addresses[random.nextInt(8)]);
        producer.setProducerName(names[random.nextInt(8)]);
        return producer;
    }

    private Category prepareCategory() {
        Category category = new Category();
        Random random = new Random();
        String[] categories = new String[]{"AGD", "RTV", "Sport", "Gaming",
                "Kuchnia"};
        category.setCategory(categories[random.nextInt(5)]);
        category.setId(random.nextLong() + 100);
        return category;
    }



    private Role prepareRole() {
        String[] roles = new String[] {"ADMIN", "USER"};
        Role role = new Role();
        Random random = new Random();
        role.setId(Long.valueOf(random.nextInt(1000)));
        role.setName(roles[random.nextInt(2)]);
        return role;
    }
}