package pl.filip.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import pl.filip.shop.dto.CartDto;
import pl.filip.shop.dto.ProductInOrderDto;
import pl.filip.shop.mapper.CartMapper;
import pl.filip.shop.mapper.ProductInOrderMapper;
import pl.filip.shop.services.BuyService;
import pl.filip.shop.services.CartService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CartController {

    private CartService cartService;
    private BuyService buyService;
    private CartMapper cartMapper;
    private ProductInOrderMapper productInOrderMapper;

    public CartController(CartService cartService,
                          BuyService buyService,
                          CartMapper cartMapper,
                          ProductInOrderMapper productInOrderMapper) {
        this.cartService = cartService;
        this.buyService = buyService;
        this.cartMapper = cartMapper;
        this.productInOrderMapper = productInOrderMapper;
    }

    @GetMapping("/getCart")
    public String cart(Model model, Principal principal) {
        List<ProductInOrderDto> products = cartService.productsInCart(principal.getName())
                .stream()
                .map(x -> productInOrderMapper.toProductInOrderDto(x))
                .collect(Collectors.toList());
        if (products.size() < 1) {
            model.addAttribute("cart_products", null);
        } else {
            model.addAttribute("cart_products", products);
        }
        return "cart";
    }

    @GetMapping("/addToCart/{id}")
    public String add(@PathVariable("id") Long id, Principal principal, Model model) {
        CartDto cart = cartMapper.toCartDto(cartService
                .addProductToCart(id, principal.getName()));
        if (cart == null) {
            model.addAttribute("cart", "in");
        }
        return "redirect:/product/" + id;
    }

    @GetMapping("/delete_product_cart/{id}")
    public String delete(@PathVariable("id") Long id, Principal principal) {
        CartDto cart = cartMapper.toCartDto(cartService
                .deleteProductFromCart(id, principal.getName()));
        return "redirect:/getCart";
    }

    @GetMapping("/clean_cart")
    public String clean(Principal principal) {
        CartDto cart = cartMapper.toCartDto(cartService
                .clean(principal.getName()));
        return "redirect:/getCart";
    }

    @GetMapping("buy_cart")
    public String buy(Principal principal) {
        String user_email = principal.getName();
        buyService.buyAllProductsFromCart(user_email);
        return "redirect:/getCart";
    }

}
