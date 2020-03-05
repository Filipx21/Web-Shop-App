package pl.filip.shop.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pl.filip.shop.model.Category;
import pl.filip.shop.model.Producer;
import pl.filip.shop.model.Product;
import pl.filip.shop.repositories.CategoryRepository;
import pl.filip.shop.repositories.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    @Test
    void shouldFindAll() throws NullPointerException {
        List<Category> categories = new ArrayList<>();
        categories.add(prepareCategory());
        categories.add(prepareCategory());
        categories.add(prepareCategory());
        categories.add(prepareCategory());

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
        Category category = prepareCategory();

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        Category result = categoryService.findById(category.getId());

        assertEquals(category, result);
    }

    @Test
    void shouldNotFindCategoryById() throws NullPointerException {
        Category category = prepareCategory();

        when(categoryRepository.findById(category.getId())).thenReturn(null);

        assertThrows(NullPointerException.class, () ->
            categoryService.findById(category.getId())
        );
    }

    @Test
    void shouldSaveCategory() throws NullPointerException {
        Category category = prepareCategory();

        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.save(category);

        assertEquals(category, result);
    }

    @Test
    void shouldNotSaveCategory() throws NullPointerException {
        Category category = prepareCategory();

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
        Category category = prepareCategory();
        category.setCategory(null);

        assertThrows(NullPointerException.class, () ->
                categoryService.save(category)
        );
    }

    @Test
    void shouldDeteleById() throws NullPointerException {
        Category category = prepareCategory();
        List<Product> products = new ArrayList<>();

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(productRepository.findAllByCategory(category)).thenReturn(products);

        Category result = categoryService.delete(category.getId());

        assertEquals(category, result);
    }

    @Test
    void shouldNotDeteleById() throws NullPointerException {
        Category category = prepareCategory();
        List<Product> products = new ArrayList<>();
        products.add(prepareProduct());

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(productRepository.findAllByCategory(category)).thenReturn(products);

        Category result = categoryService.delete(category.getId());

        assertNull(result);
    }

    @Test
    void shouldThrowNullPointerDeteleById() throws NullPointerException {
        Category category = prepareCategory();

        when(categoryRepository.findById(category.getId())).thenReturn(null);

        assertThrows(NullPointerException.class, () ->
                categoryService.delete(category.getId())
        );
    }

    private Product prepareProduct() {
        Random random = new Random();
        Product product = new Product();
        String[] names = new String[]{"Rower", "Rolki", "Myszka",
                "Garnek", "Kawa", "Woda", "Sol", "Lampa", "Posciel"};
        product.setId(random.nextLong() + 100);
        product.setProductName(names[random.nextInt(8)]);
        product.setDescript(names[random.nextInt(8)]);
        product.setQuantity(random.nextInt(100) + 1);
        product.setCost(new BigDecimal(random.nextDouble() * 100 + 1));
        product.setProducer(preapreProducer());
        product.setCategory(prepareCategory());
        product.setCreatedDate(LocalDate.now());
        return product;
    }

    private Producer preapreProducer() {
        Random random = new Random();
        Producer producer = new Producer();
        String[] names = new String[]{"PCC", "Wapniaki", "JA", "Mavos",
                "Intermodal", "DCOM", "Oldb", "Michalki", "Test"};
        String[] addresses = new String[]{"Ruska 55", "Kamien 2", "Wroclaw 44",
                "Warszawa 555", "Wilcza 43", "sfdfsd 34", "Testowa 33",
                "Testowa 22", "Testowa 13"};
        producer.setId(random.nextLong() + 100);
        producer.setProducerName(names[random.nextInt(8)]);
        producer.setAddress(addresses[random.nextInt(8)]);
        return producer;
    }

    private Category prepareCategory() {
        Random random = new Random();
        Category category = new Category();
        String[] categories = new String[]{"AGD", "RTV", "Sport", "Gaming",
                "Kuchnia"};
        category.setId(random.nextLong() + 100);
        category.setCategory(categories[random.nextInt(5)]);
        return category;
    }
}
