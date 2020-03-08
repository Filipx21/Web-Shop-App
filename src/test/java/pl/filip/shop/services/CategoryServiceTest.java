package pl.filip.shop.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pl.filip.shop.model.Category;
import pl.filip.shop.model.Product;
import pl.filip.shop.repositories.CategoryRepository;
import pl.filip.shop.repositories.ProductRepository;
import pl.filip.shop.resource.DataTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    CategoryService categoryService;

    private DataTest dataTest;

    @BeforeEach
    void init() {
        dataTest = new DataTest();
    }

    @Test
    void shouldFindAll() throws NullPointerException {
        List<Category> categories = new ArrayList<>();
        categories.add(dataTest.prepareCategory());
        categories.add(dataTest.prepareCategory());
        categories.add(dataTest.prepareCategory());
        categories.add(dataTest.prepareCategory());

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.findAll();

        assertEquals(categories, result);
    }

    @Test
    void shouldThrowNullPointerFindAll() throws NullPointerException {
        List<Category> categories = new ArrayList<>();

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.findAll();

        assertEquals(categories, result);
    }

    @Test
    void shouldFindCategoryById() throws NullPointerException {
        Category category = dataTest.prepareCategory();

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        Category result = categoryService.findById(category.getId());

        assertEquals(category, result);
    }

    @Test
    void shouldNotFindCategoryById() throws NullPointerException {
        Category category = dataTest.prepareCategory();

        when(categoryRepository.findById(category.getId())).thenReturn(null);

        assertThrows(NullPointerException.class, () ->
            categoryService.findById(category.getId())
        );
    }

    @Test
    void shouldSaveCategory() throws NullPointerException {
        Category category = dataTest.prepareCategory();

        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.save(category);

        assertEquals(category, result);
    }

    @Test
    void shouldNotSaveCategory() throws NullPointerException {
        Category category = dataTest.prepareCategory();

        when(categoryRepository.save(category)).thenReturn(null);

        Category result = categoryService.save(category);

        assertNull(result);
    }

    @Test
    void shouldThrowNullPointerSaveCategory() throws NullPointerException {
        Category category = null;

        assertThrows(NullPointerException.class, () ->
                categoryService.save(category)
        );
    }

    @Test
    void shouldThrowNullPointerSaveCategoryIsEmpty() throws NullPointerException {
        Category category = dataTest.prepareCategory();
        category.setCategory(null);

        assertThrows(NullPointerException.class, () ->
                categoryService.save(category)
        );
    }

    @Test
    void shouldDeteleById() throws NullPointerException {
        Category category = dataTest.prepareCategory();
        List<Product> products = new ArrayList<>();

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(productRepository.findAllByCategory(category)).thenReturn(products);

        Category result = categoryService.delete(category.getId());

        assertEquals(category, result);
    }

    @Test
    void shouldNotDeteleById() throws NullPointerException {
        Category category = dataTest.prepareCategory();
        List<Product> products = new ArrayList<>();
        products.add(dataTest.prepareProduct());

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(productRepository.findAllByCategory(category)).thenReturn(products);

        Category result = categoryService.delete(category.getId());

        assertNull(result);
    }

    @Test
    void shouldThrowNullPointerDeteleById() throws NullPointerException {
        Category category = dataTest.prepareCategory();

        when(categoryRepository.findById(category.getId())).thenReturn(null);

        assertThrows(NullPointerException.class, () ->
                categoryService.delete(category.getId())
        );
    }
}
