package pl.filip.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import pl.filip.shop.dto.OrderUserDto;
import pl.filip.shop.dto.ProductDto;
import pl.filip.shop.dto.UserDetailsDto;
import pl.filip.shop.mapper.OrderUserMapper;
import pl.filip.shop.mapper.ProductMapper;
import pl.filip.shop.mapper.UserMapper;
import pl.filip.shop.services.BuyService;
import pl.filip.shop.services.CategoryService;
import pl.filip.shop.services.UserService;
import pl.filip.shop.services.ProductService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdministratorController {

    private UserService userService;
    private BuyService buyService;
    private ProductService productService;
    private CategoryService categoryService;
    private UserMapper userMapper;
    private ProductMapper productMapper;
    private OrderUserMapper orderUserMapper;

    public AdministratorController(UserService userService,
                                   BuyService buyService,
                                   ProductService productService,
                                   CategoryService categoryService,
                                   UserMapper userMapper,
                                   ProductMapper productMapper,
                                   OrderUserMapper orderUserMapper) {
        this.userService = userService;
        this.buyService = buyService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.userMapper = userMapper;
        this.productMapper = productMapper;
        this.orderUserMapper = orderUserMapper;
    }

    @GetMapping("/administrator")
    public String adminPanel(Model model, Principal principal) {
        String email = principal.getName();
        UserDetailsDto user = userMapper.toUserDto(userService.findByEmail(email));
        model.addAttribute("user", user);
        return "administrator";
    }

    @GetMapping("/administrator/users")
    public String allUsers(Model model) {
        List<UserDetailsDto> users = userService.findAll()
                .stream()
                .map(x -> userMapper.toUserDto(x))
                .collect(Collectors.toList());
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
        model.addAttribute("product", new ProductDto());
        model.addAttribute("all_category", categoryService.findAll());
        return "new_product";
    }

    @PostMapping("/administrator/add_product")
    public String addProduct(@Valid @ModelAttribute("product") ProductDto product,
                             BindingResult result) {
        if (result.hasErrors()){
            return "new_product";
        }
        boolean isExist = false;
        if(product.getId() != null){
            isExist = true;
        }
        ProductDto productDto = productMapper.toProductDto(productService
                .saveProduct(productMapper.toProduct(product)));
        if (productDto != null) {
            if (isExist) {
                return "redirect:/product/" + product.getId();
            }
            return "redirect:/administrator";
        }
        return "external_error.html";
    }

    @GetMapping("/administrator/edit_product/{id}")
    public String editProduct(Model model, @PathVariable("id") Long id) {
        ProductDto object = productMapper.toProductDto(productService.findById(id));
        if (object != null) {
            model.addAttribute("product_details", object);
            model.addAttribute("all_category", categoryService.findAll());
            return "edit_product.html";
        }
        return "not_found.html";
    }

    @GetMapping("/administrator/orders")
    public String getAllOrders(Model model) {
        List<OrderUserDto> orders = buyService.findAllNotSent()
                .stream()
                .map(x -> orderUserMapper.toOrderUserDto(x))
                .collect(Collectors.toList());
        Collections.reverse(orders);
        model.addAttribute("orders", orders);
        return "users_orders";
    }

    @GetMapping("/administrator/sent_orders")
    public String getOrders(Model model) {
        List<OrderUserDto> orders = buyService.findAllSent()
                .stream()
                .map(x -> orderUserMapper.toOrderUserDto(x))
                .collect(Collectors.toList());
        Collections.reverse(orders);
        model.addAttribute("orders", orders);
        return "users_orders";
    }

    @GetMapping("/administrator/orders/{id}")
    public String sendProduct(@PathVariable("id") Long id){
        OrderUserDto order = orderUserMapper.toOrderUserDto(buyService.send(id));
        return "redirect:/administrator/orders";
    }

    @GetMapping("/administrator/a_orders/{id}")
    public String sendAProduct(@PathVariable("id") Long id){
        OrderUserDto order = orderUserMapper.toOrderUserDto(buyService.send(id));
        return "redirect:/administrator/sent_orders";
    }
}
