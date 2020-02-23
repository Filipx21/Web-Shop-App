package pl.filip.shop.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.filip.shop.model.Product;
import pl.filip.shop.services.BuyService;
import pl.filip.shop.services.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService, BuyService buyService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String products(Model model,
                           @RequestParam(value = "productName", required = false,defaultValue = "") String productName,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);
        Page<Product> productPage;

        if (!productName.equals("")) {
            productPage = productService
                    .findProductsByName(productName, PageRequest.of(currentPage - 1, pageSize));
        } else {
            productPage = productService
                    .findAllProduct(PageRequest.of(currentPage - 1, pageSize));
        }

        if (productPage == null) {
            return "not_found.html";
        }

        model.addAttribute("productPage", productPage);

        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
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
