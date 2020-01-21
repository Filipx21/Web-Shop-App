package pl.filip.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.filip.shop.model.Product;
import pl.filip.shop.services.ProductService;

@Controller
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String products(Model model, @RequestParam(
            value = "productName",
            required = false) String productName) {
        if (productName == null || productName.equals("")) {
            model.addAttribute("list_products", productService.findAllProduct());
        } else {
            model.addAttribute("list_products", productService.findProductsByName(productName));
        }
        return "products.html";
    }

    @GetMapping("/product/{id}")
    public String product(Model model, @PathVariable("id") Long id) {
        Product object = productService.findById(id);
        if (object != null) {
            model.addAttribute("product_details", object);
            return "product.html";
        }
        return "not_found.html";
    }

    @GetMapping("/buy_product/{id}")
    public String buyProduct(@PathVariable("id") Long id) {


        return "buy.html";
    }


    @GetMapping("/add_product")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        return "new_product.html";
    }

    @PostMapping("/add_product")
    public String addProduct(Product product) {
        Product object = productService.saveProduct(product);
        if (object != null) {
            return "redirect:/products";
        }
        return "external_error.html";
    }

    @GetMapping("/edit_product/{id}")
    public String editProduct(Model model, @PathVariable("id") Long id) {
        Product object = productService.findById(id);
        if (object != null) {
            model.addAttribute("product_details", object);
            return "edit_product.html";
        }
        return "not_found.html";
    }

    @GetMapping("/delete_product/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }

}
