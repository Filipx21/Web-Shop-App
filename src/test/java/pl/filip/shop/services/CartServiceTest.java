package pl.filip.shop.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pl.filip.shop.model.Cart;
import pl.filip.shop.model.ProductInOrder;
import pl.filip.shop.model.SysUser;
import pl.filip.shop.model.Product;
import pl.filip.shop.repositories.CartRepository;
import pl.filip.shop.repositories.ProductRepository;
import pl.filip.shop.repositories.SysUserRepository;
import pl.filip.shop.resource.DataTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private DataTest dataTest;

    @BeforeEach
    void init() {
        dataTest = new DataTest();
    }

    @Test
    void shouldFindProductsInCart() {
        SysUser user = dataTest.prepareUser();
        Cart cart = dataTest.prepareCart();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cartRepository.findAllBySysUserAndInUse(user, true)).thenReturn(List.of(cart));

        List<ProductInOrder> result = cartService.productsInCart(user.getEmail());

        assertEquals(cart.getProducts().get(0), result.get(0));
    }

    @Test
    void shouldThrowNullExceptionFindProductsInCart() {
        SysUser user = dataTest.prepareUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cartRepository.findAllBySysUserAndInUse(user, true)).thenThrow(IndexOutOfBoundsException.class);

        assertThrows( IndexOutOfBoundsException.class, () ->cartService.productsInCart(user.getEmail()));
    }

    @Test
    void shouldThrowIndexOutOfBoundsExceptionFindProductsInCart() {
        SysUser user = dataTest.prepareUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertNull(cartService.productsInCart(user.getEmail()));
    }

    @Test
    void shouldAddProductToCart() {
        SysUser user = dataTest.prepareUser();
        Product product = dataTest.prepareProduct();
        Cart cart = dataTest.prepareCart();

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
        SysUser user = dataTest.prepareUser();
        Cart cart = dataTest.prepareCart();
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
        SysUser user = dataTest.prepareUser();
        Cart cart = dataTest.prepareCart();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cartRepository.findAllBySysUserAndInUse(user, true)).thenReturn(List.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart result = cartService.clean(user.getEmail());
        cart.setInUse(false);

        assertEquals(cart, result);
    }

    @Test
    void shouldNotClean() {
        SysUser user = dataTest.prepareUser();
        Cart cart = dataTest.prepareCart();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cartRepository.findAllBySysUserAndInUse(user, true)).thenReturn(List.of(cart));
        when(cartRepository.save(cart)).thenThrow(NullPointerException.class);

        Cart result = cartService.clean(user.getEmail());

        assertNull(result);
    }
}