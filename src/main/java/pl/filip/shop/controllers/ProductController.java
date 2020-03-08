package pl.filip.shop.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import pl.filip.shop.dto.ProductDto;
import pl.filip.shop.mapper.ProductMapper;
import pl.filip.shop.services.CategoryService;
import pl.filip.shop.services.ProductService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ProductController {

    private ProductService productService;
    private CategoryService categoryService;
    private ProductMapper productMapper;

    public ProductController(ProductService productService,
                             CategoryService categoryService, ProductMapper productMapper) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
    }

    @GetMapping("/products")
    public String products(Model model,
                           @RequestParam(value = "productName", required = false,defaultValue = "") String productName,
                           @RequestParam(value = "category", required = false, defaultValue = "") String category,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);
        Page<ProductDto> productPage;

        if(!category.isEmpty()){
            long category_id = Long.parseLong(category);
            productPage = fillPage(productService.findProductsByCategory(category_id)
                    .stream()
                    .map(productMapper::toProductDto)
                    .collect(Collectors.toList()), PageRequest.of(currentPage - 1, pageSize));
        } else if (!productName.equals("")) {
            productPage = fillPage(productService.findProductsByName(productName)
                    .stream()
                    .map(productMapper::toProductDto)
                    .collect(Collectors.toList()), PageRequest.of(currentPage - 1, pageSize));
        } else {
            productPage = fillPage(productService.findAllProduct()
                    .stream()
                    .map(productMapper::toProductDto)
                    .collect(Collectors.toList()), PageRequest.of(currentPage - 1, pageSize));
        }

        if (!productPage.hasContent()) {
            return "not_found.html";
        }

        model.addAttribute("productPage", productPage);
        model.addAttribute("all_category", categoryService.findAll());

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
        ProductDto productDto = productMapper.toProductDto(productService.findById(id));
        if (productDto != null) {
            model.addAttribute("product_details", productDto);
            return "product.html";
        }
        return "not_found.html";
    }

    @GetMapping("/buy_product/{id}")
    public String buyProduct(@PathVariable("id") Long id) {


        return "buy.html";
    }

    @GetMapping("/delete_product/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }

    private Page<ProductDto> fillPage(List<ProductDto> productList, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ProductDto> products;

        if (productList.size() < startItem) {
            products = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, productList.size());
            products = productList.subList(startItem, toIndex);
        }
        return new PageImpl<>(products,
                PageRequest.of(currentPage, pageSize),
                productList.size());
    }

}
