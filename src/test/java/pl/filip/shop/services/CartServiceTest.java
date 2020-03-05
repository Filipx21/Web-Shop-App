package pl.filip.shop.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pl.filip.shop.model.Cart;
import pl.filip.shop.model.OrderUser;
import pl.filip.shop.model.ProductInOrder;
import pl.filip.shop.model.SysUser;
import pl.filip.shop.model.Category;
import pl.filip.shop.model.Producer;
import pl.filip.shop.model.Role;
import pl.filip.shop.model.Product;
import pl.filip.shop.repositories.CartRepository;
import pl.filip.shop.repositories.ProductRepository;
import pl.filip.shop.repositories.SysUserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    CartRepository cartRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    SysUserRepository userRepository;

    @InjectMocks
    CartService cartService;

    @Test
    void shouldFindProductsInCart() {
        SysUser user = prepareUser();
        Cart cart = prepareCart();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cartRepository.findAllBySysUserAndInUse(user, true)).thenReturn(List.of(cart));

        List<ProductInOrder> result = cartService.productsInCart(user.getEmail());

        assertEquals(cart.getProducts().get(0), result.get(0));
    }

    @Test
    void shouldThrowNullExceptionFindProductsInCart() {
        SysUser user = prepareUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cartRepository.findAllBySysUserAndInUse(user, true)).thenThrow(IndexOutOfBoundsException.class);

        assertThrows( IndexOutOfBoundsException.class, () ->cartService.productsInCart(user.getEmail()));
    }

    @Test
    void shouldThrowIndexOutOfBoundsExceptionFindProductsInCart() {
        SysUser user = prepareUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertNull(cartService.productsInCart(user.getEmail()));
    }

    @Test
    void shouldAddProductToCart() {
        SysUser user = prepareUser();
        Product product = prepareProduct();
        Cart cart = prepareCart();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(cartRepository.findAllBySysUserAndInUse(user, true)).thenReturn(List.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart result = cartService.addProductToCart(product.getId(), user.getEmail());
        List<ProductInOrder> products = cart.getProducts();
        products.add(new ProductInOrder(product));
        cart.setProducts(products);

        assertEquals(cart, result);
    }

    @Test
    void shouldDeleteProductFromCart() {
        SysUser user = prepareUser();
        Cart cart = prepareCart();
        cart.setInUse(true);
        Long id = cart.getProducts().get(0).getId();


        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cartRepository.findAllBySysUserAndInUse(user, true)).thenReturn(List.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart result = cartService.deleteProductFromCart(id, user.getEmail());

        cart.setProducts(cart.getProducts()
                .stream()
                .filter(x -> !x.getId().equals(id))
                .collect(Collectors.toList()));
        assertEquals(cart, result);
    }

    @Test
    void shouldClean() {
        SysUser user = prepareUser();
        Cart cart = prepareCart();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cartRepository.findAllBySysUserAndInUse(user, true)).thenReturn(List.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart result = cartService.clean(user.getEmail());
        cart.setInUse(false);

        assertEquals(cart, result);
    }

    @Test
    void shouldNotClean() {
        SysUser user = prepareUser();
        Cart cart = prepareCart();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cartRepository.findAllBySysUserAndInUse(user, true)).thenReturn(List.of(cart));
        when(cartRepository.save(cart)).thenThrow(NullPointerException.class);

        Cart result = cartService.clean(user.getEmail());

        assertNull(result);
    }

    private Cart prepareCart() {
        Random random = new Random();
        Cart cart = new Cart();
        cart.setId(Math.abs(random.nextLong() + 100));
        cart.setProducts(prepareProductInOrder());
        cart.setInUse(false);
        cart.setSysUser(prepareUser());
        return cart;
    }

    private OrderUser prepareOrderUser() {
        Random random = new Random();
        OrderUser orderUser = new OrderUser();
        orderUser.setId(Math.abs(random.nextLong() + 100));
        orderUser.setFinish(false);
        orderUser.setProductInOrders(prepareProductInOrder());
        orderUser.setSysUser(prepareUser());
        orderUser.setDone(true);
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
        Random random = new Random();
        return allProducts
                .stream()
                .map(ProductInOrder::new)
                .filter(x -> {
                    x.setId(Math.abs(random.nextLong() + 100));
                    return true;
                })
                .collect(Collectors.toList());
    }

    private Product prepareProduct() {
        Random random = new Random();
        String[] names = new String[]{"Rower", "Rolki", "Myszka",
                "Garnek", "Kawa", "Woda", "Sol", "Lampa", "Posciel"};
        Product product = new Product();
        product.setId(Math.abs(random.nextLong() + 100));
        product.setProductName(names[random.nextInt(8)]);
        product.setDescript(names[random.nextInt(8)]);
        product.setQuantity(random.nextInt(100) + 1);
        product.setCategory(prepareCategory());
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
        user.setRoles(roles);
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
        producer.setAddress(addresses[random.nextInt(8)]);
        producer.setId(random.nextLong() + 100);
        producer.setAddress(addresses[random.nextInt(8)]);
        producer.setProducerName(names[random.nextInt(8)]);
        return producer;
    }

    private Category prepareCategory() {
        Category category = new Category();
        Random random = new Random();
        category.setId(random.nextLong() + 100);
        String[] categories = new String[]{"AGD", "RTV", "Sport", "Gaming",
                "Kuchnia"};
        category.setCategory(categories[random.nextInt(5)]);

        return category;
    }

    private Role prepareRole() {
        Random random = new Random();
        String[] roles = new String[] {"ADMIN", "USER"};
        Role role = new Role();
        role.setId(Long.valueOf(random.nextInt(1000)));
        role.setName(roles[random.nextInt(2)]);
        return role;
    }
}