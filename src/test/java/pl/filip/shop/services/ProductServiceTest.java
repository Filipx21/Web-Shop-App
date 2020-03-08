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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    ProductService productService;

    private DataTest dataTest;

    @BeforeEach
    void init() {
        dataTest = new DataTest();
    }

    @Test
    void shouldDeleteById() throws NullPointerException {
        Product product = dataTest.prepareProduct();

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        assertTrue(productService.deleteProductById(product.getId()));
    }

    @Test
    void shouldThrowNullPointerDeleteById() throws NullPointerException {
        Product product = dataTest.prepareProduct();

        when(productRepository.findById(product.getId())).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () ->
                productService.deleteProductById(product.getId())
        );
    }

    @Test
    void shouldFindNullAndThrowPointerDeleteById() throws NullPointerException {
        Product product = dataTest.prepareProduct();

        when(productRepository.findById(product.getId())).thenReturn(null);

        assertThrows(NullPointerException.class, () ->
                productService.deleteProductById(product.getId())
        );
    }

    @Test
    void shouldSaveProduct() throws NullPointerException {
        Product product = dataTest.prepareProduct();

        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.saveProduct(product);

        assertEquals(product, result);
    }

    @Test
    void shouldThrowNullPointerSaveAsNull() throws NullPointerException {
        Product product = null;

        assertThrows(NullPointerException.class, () ->
                productService.saveProduct(product)
        );
    }

    @Test
    void shouldSetDate() throws NullPointerException {
        Product product = dataTest.prepareProduct();
        product.setCreatedDate(null);
        Product expected = product;
        expected.setCreatedDate(LocalDate.now());

        when(productRepository.save(product)).thenReturn(expected);

        Product result = productService.saveProduct(product);

        assertEquals(expected.getCreatedDate(), result.getCreatedDate());
    }

    @Test
    void shouldReturnNullBadSave() throws NullPointerException {
        Product product = dataTest.prepareProduct();

        when(productRepository.save(product)).thenReturn(null);

        Product result = productService.saveProduct(product);

        assertNull(result);
    }

    @Test
    void shouldFindById() throws NullPointerException {
        Product product = dataTest.prepareProduct();

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Product result = productService.findById(product.getId());

        assertEquals(product, result);
    }

    @Test
    void shouldThrowNullPointerFindById() throws NullPointerException {
        when(productRepository.findById(1L)).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () ->
                productService.findById(1L)
        );
    }

    @Test
    void shouldFindPageWithProducts() throws NullPointerException {
        List<Product> products = new ArrayList<>();
        products.add(dataTest.prepareProduct());
        products.add(dataTest.prepareProduct());
        products.add(dataTest.prepareProduct());
        products.add(dataTest.prepareProduct());

        when(productRepository.findAll()).thenReturn(products);

        List<Product> productsPage = productService.findAllProduct();

        assertEquals(products, productsPage);
        assertEquals(products.size(), productsPage.size());
        assertEquals(products.get(0), productsPage.get(0));
    }


    @Test
    void shouldThrowIndexOutWithProducts() throws NullPointerException {
        List<Product> products = new ArrayList<>();
        products.add(dataTest.prepareProduct());
        products.add(dataTest.prepareProduct());
        products.add(dataTest.prepareProduct());
        products.add(dataTest.prepareProduct());

        when(productRepository.findAll()).thenReturn(products);

        List<Product> productsPage = productService.findAllProduct();

        assertThrows(IndexOutOfBoundsException.class, () ->
                productsPage.get(10)
        );
    }

    @Test
    void shouldThrowNullPointerWithProducts() throws NullPointerException {
        List<Product> products = new ArrayList<>();

        when(productRepository.findAll()).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () ->
                productService.findAllProduct()
        );
    }

    @Test
    void shouldFindAllByName() throws NullPointerException {
        List<Product> products = new ArrayList<>();
        Product product = dataTest.prepareProduct();
        product.setProductName("Test");
        products.add(product);
        products.add(product);
        products.add(product);
        products.add(product);

        when(productRepository.findAllByProductName("Test")).thenReturn(products);

        List<Product> productsPage = productService.findProductsByName("Test");

        assertEquals(products, productsPage);
        assertEquals(products.size(), productsPage.size());
        assertEquals(products.get(0), productsPage.get(0));
    }


    @Test
    void shouldNotFindAllByName() throws NullPointerException {
        List<Product> products = new ArrayList<>();
        products.add(dataTest.prepareProduct());
        products.add(dataTest.prepareProduct());
        products.add(dataTest.prepareProduct());
        products.add(dataTest.prepareProduct());
        List<Product> empty = new ArrayList<>();

        when(productRepository.findAllByProductName("Test")).thenReturn(empty);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> productsPage = productService.findProductsByName("Test");

        assertEquals(products, productsPage);
        assertEquals(products.size(), productsPage.size());
        assertEquals(products.get(0), productsPage.get(0));
    }


    @Test
    void shouldFindAllByCategory() throws NullPointerException {
        List<Product> products = new ArrayList<>();
        Product product = dataTest.prepareProduct();
        product.setCategory(dataTest.prepareCategory());
        products.add(product);
        products.add(product);
        products.add(product);
        products.add(product);

        when(categoryRepository.findById(product.getCategory().getId())).thenReturn(Optional.of(product.getCategory()));
        when(productRepository.findAllByCategory(product.getCategory())).thenReturn(products);

        List<Product> productsPage = productService.findProductsByCategory(product.getCategory().getId());

        assertEquals(products, productsPage);
        assertEquals(products.size(), productsPage.size());
        assertEquals(products.get(0), productsPage.get(0));
    }



    @Test
    void shouldNotFindAllByCategory() throws NullPointerException {
        List<Product> products = new ArrayList<>();
        Product product = dataTest.prepareProduct();
        Category category = dataTest.prepareCategory();
        product.setCategory(dataTest.prepareCategory());
        products.add(product);
        products.add(product);
        products.add(product);
        products.add(product);
        List<Product> empty = new ArrayList<>();

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(productRepository.findAllByCategory(category)).thenReturn(empty);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> productsPage = productService.findProductsByCategory(category.getId());

        assertEquals(products, productsPage);
        assertEquals(products.size(), productsPage.size());
        assertEquals(products.get(0), productsPage.get(0));
    }

}