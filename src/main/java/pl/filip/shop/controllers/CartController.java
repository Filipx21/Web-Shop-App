package pl.filip.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.filip.shop.model.Cart;
import pl.filip.shop.model.OrderUser;
import pl.filip.shop.model.ProductInOrder;
import pl.filip.shop.services.BuyService;
import pl.filip.shop.services.CartService;

import java.security.Principal;
import java.util.List;

@Controller
public class CartController {

    private CartService cartService;
    private BuyService buyService;

    public CartController(CartService cartService, BuyService buyService) {
        this.cartService = cartService;
        this.buyService = buyService;
    }

    @GetMapping("/getCart")
    public String cart(Model model, Principal principal) {
        List<ProductInOrder> products = cartService.productsInCart(principal.getName());
        if (products == null) {
            model.addAttribute("cart_products", null);
        } else if (products.size() < 1) {
            model.addAttribute("cart_products", null);
        } else {
            model.addAttribute("cart_products", products);
        }
        return "cart";
    }

    @GetMapping("/addToCart/{id}")
    public String add(@PathVariable("id") Long id, Principal principal, Model model) {
        Cart cart = cartService.addProductToCart(id, principal.getName());
        if (cart == null) {
            model.addAttribute("cart", "in");
        }
        return "redirect:/product/" + id;
    }

    @GetMapping("/delete_product_cart/{id}")
    public String delete(@PathVariable("id") Long id, Principal principal) {
        Cart cart = cartService.deleteProductFromCart(id, principal.getName());
        return "redirect:/getCart";
    }

    @GetMapping("/clean_cart")
    public String clean(Principal principal) {
        Cart clean = cartService.clean(principal.getName());
        return "redirect:/getCart";
    }

    @GetMapping("buy_cart")
    public String buy(Principal principal) {
        String user_email = principal.getName();
        OrderUser orderUser = buyService.buyAllProductsFromCart(user_email);
        return "redirect:/getCart";
    }

}
