package pl.filip.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.filip.shop.dto.CategoryDto;
import pl.filip.shop.model.Category;
import pl.filip.shop.services.CategoryService;

import javax.validation.Valid;

@Controller
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
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
        Category newCat = new Category(category.getCategory());
        Category cat = categoryService.save(newCat);
        if(cat != null) {
            return "redirect:/administrator";
        }
        return "not_found";
    }

    @GetMapping("/administrator/categories")
    public String categories(Model model){
        model.addAttribute("categories", categoryService.findAll());
        return "categories";
    }

    @GetMapping("/administrator/categories/delete/{id}")
    public String categories(@PathVariable("id") Long id){
        Category deleted = categoryService.delete(id);
        return "redirect:/administrator/categories";
    }

}
