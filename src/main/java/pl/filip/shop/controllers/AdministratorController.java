package pl.filip.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.filip.shop.model.OrderUser;
import pl.filip.shop.model.Product;
import pl.filip.shop.model.SysUser;
import pl.filip.shop.services.BuyService;
import pl.filip.shop.services.CategoryService;
import pl.filip.shop.services.UserService;
import pl.filip.shop.services.ProductService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
public class AdministratorController {

    private UserService userService;
    private BuyService buyService;
    private ProductService productService;
    private CategoryService categoryService;

    public AdministratorController(UserService userService,
                                   BuyService buyService,
                                   ProductService productService,
                                   CategoryService categoryService) {
        this.userService = userService;
        this.buyService = buyService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/administrator")
    public String adminPanel(Model model, Principal principal) {
        String email = principal.getName();
        SysUser user = userService.findByEmail(email);
        model.addAttribute("user", user);
        return "administrator";
    }

    @GetMapping("/administrator/users")
    public String allUsers(Model model) {
        List<SysUser> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/administrator/user/{id}")
    public String blockAcc(@PathVariable("id") Long id){
        boolean b = userService.blockAcc(id);
        return "redirect:/administrator/users";
    }

    @GetMapping("/administrator/add_product")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("all_category", categoryService.findAll());
        return "new_product";
    }

    @PostMapping("/administrator/add_product")
    public String addProduct(@Valid @ModelAttribute("product") Product product,
                             BindingResult result) {
        if (result.hasErrors()){
            return "new_product";
        }
        boolean isExist = false;
        if(product.getId() != null){
            isExist = true;
        }
        Product object = productService.saveProduct(product);
        if (object != null) {
            if (isExist) {
                return "redirect:/product/" + product.getId();
            }
            return "redirect:/administrator";
        }
        return "external_error.html";
    }

    @GetMapping("/administrator/edit_product/{id}")
    public String editProduct(Model model, @PathVariable("id") Long id) {
        Product object = productService.findById(id);
        if (object != null) {
            model.addAttribute("product_details", object);
            model.addAttribute("all_category", categoryService.findAll());
            return "edit_product.html";
        }
        return "not_found.html";
    }

    @GetMapping("/administrator/orders")
    public String getAllOrders(Model model) {
        List<OrderUser> orders = buyService.findAllNotSent();
        Collections.reverse(orders);
        model.addAttribute("orders", orders);
        return "users_orders";
    }

    @GetMapping("/administrator/sent_orders")
    public String getOrders(Model model) {
        List<OrderUser> orders = buyService.findAllSent();
        Collections.reverse(orders);
        model.addAttribute("orders", orders);
        return "users_orders";
    }

    @GetMapping("/administrator/orders/{id}")
    public String sendProduct(@PathVariable("id") Long id){
        OrderUser order = buyService.send(id);
        return "redirect:/administrator/orders";
    }

    @GetMapping("/administrator/a_orders/{id}")
    public String sendAProduct(@PathVariable("id") Long id){
        OrderUser order = buyService.send(id);
        return "redirect:/administrator/sent_orders";
    }


}
