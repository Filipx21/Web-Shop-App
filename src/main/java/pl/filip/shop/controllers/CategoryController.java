package pl.filip.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import pl.filip.shop.dto.CategoryDto;
import pl.filip.shop.mapper.CategoryMapper;
import pl.filip.shop.services.CategoryService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CategoryController {

    private CategoryService categoryService;
    private CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("/administrator/category")
    public String newCategory(Model model){
        model.addAttribute("category", new CategoryDto());
        return "new_category";
    }

    @PostMapping("/administrator/category")
    public String addCategory(@Valid @ModelAttribute("category") CategoryDto category, BindingResult result){
        if (result.hasErrors()){
            return "new_category";
        }
        if(category != null) {
            categoryService.save(categoryMapper.toCategory(category));
            return "redirect:/administrator";
        }
        return "not_found";
    }

    @GetMapping("/administrator/categories")
    public String categories(Model model){
        List<CategoryDto> categories = categoryService.findAll()
                .stream()
                .map(x -> categoryMapper.toCategoryDto(x))
                .collect(Collectors.toList());
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/administrator/categories/delete/{id}")
    public String categories(@PathVariable("id") Long id){
        CategoryDto category = categoryMapper.toCategoryDto(categoryService.delete(id));
        return "redirect:/administrator/categories";
    }

}
