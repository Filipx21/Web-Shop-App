package pl.filip.shop.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.filip.shop.model.Category;
import pl.filip.shop.model.Producer;
import pl.filip.shop.model.Product;
import pl.filip.shop.repositories.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    void shouldDeleteById() throws NullPointerException {
        Product product = prepareProduct();

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        assertTrue(productService.deleteProductById(product.getId()));
    }

    @Test
    void shouldThrowNullPointerDeleteById() throws NullPointerException {
        Product product = prepareProduct();

        when(productRepository.findById(product.getId())).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () ->
                productService.deleteProductById(product.getId())
        );
    }

    @Test
    void shouldFindNullAndThrowPointerDeleteById() throws NullPointerException {
        Product product = prepareProduct();

        when(productRepository.findById(product.getId())).thenReturn(null);

        assertThrows(NullPointerException.class, () ->
                productService.deleteProductById(product.getId())
        );
    }

    @Test
    void shouldSaveProduct() throws NullPointerException {
        Product product = prepareProduct();

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
        Product product = prepareProduct();
        product.setCreatedDate(null);
        Product expected = product;
        expected.setCreatedDate(LocalDate.now());

        when(productRepository.save(product)).thenReturn(expected);

        Product result = productService.saveProduct(product);

        assertEquals(expected.getCreatedDate(), result.getCreatedDate());
    }

    @Test
    void shouldReturnNullBadSave() throws NullPointerException {
        Product product = prepareProduct();

        when(productRepository.save(product)).thenReturn(null);

        Product result = productService.saveProduct(product);

        assertNull(result);
    }

    @Test
    void shouldFindById() throws NullPointerException {
        Product product = prepareProduct();

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
        products.add(prepareProduct());
        products.add(prepareProduct());
        products.add(prepareProduct());
        products.add(prepareProduct());
        Pageable pageable = PageRequest.of(1, 1);
        Page page = fillPage(products, pageable);

        when(productRepository.findAll()).thenReturn(products);

        Page<Product> productsPage = productService.findAllProduct(pageable);

        assertEquals(page, productsPage);
        assertEquals(page.getContent().size(), productsPage.getContent().size());
        assertEquals(page.getContent().get(0), productsPage.getContent().get(0));
    }

    @Test
    void shouldThrowIndexOutWithProducts() throws NullPointerException {
        List<Product> products = new ArrayList<>();
        products.add(prepareProduct());
        products.add(prepareProduct());
        products.add(prepareProduct());
        products.add(prepareProduct());
        Pageable pageable = PageRequest.of(1, 1);
        Page page = fillPage(products, pageable);

        when(productRepository.findAll()).thenReturn(products);

        Page<Product> productsPage = productService.findAllProduct(pageable);

        assertThrows(IndexOutOfBoundsException.class, () ->
                productsPage.getContent().get(1)
        );
    }

    @Test
    void shouldThrowNullPointerWithProducts() throws NullPointerException {
        List<Product> products = new ArrayList<>();
        Pageable pageable = PageRequest.of(1, 1);
        Page page = fillPage(products, pageable);

        when(productRepository.findAll()).thenReturn(null);

        assertThrows(NullPointerException.class, () ->
                productService.findAllProduct(pageable)
        );
    }

    @Test
    void shouldFindAllByName() throws NullPointerException {
        List<Product> products = new ArrayList<>();
        Product product = prepareProduct();
        product.setProductName("Test");
        products.add(product);
        products.add(product);
        products.add(product);
        products.add(product);
        Pageable pageable = PageRequest.of(1, 1);
        Page page = fillPage(products, pageable);

        when(productRepository.findAllByProductName("Test")).thenReturn(products);

        Page<Product> productsPage = productService.findProductsByName("Test", pageable);

        assertEquals(page, productsPage);
        assertEquals(page.getContent().size(), productsPage.getContent().size());
        assertEquals(page.getContent().get(0), productsPage.getContent().get(0));
    }

    @Test
    void shouldNotFindAllByName() throws NullPointerException {
        List<Product> products = new ArrayList<>();
        products.add(prepareProduct());
        products.add(prepareProduct());
        products.add(prepareProduct());
        products.add(prepareProduct());
        Pageable pageable = PageRequest.of(1, 1);
        Page page = fillPage(products, pageable);
        List<Product> empty = new ArrayList<>();

        when(productRepository.findAllByProductName("Test")).thenReturn(empty);
        when(productRepository.findAll()).thenReturn(products);

        Page<Product> productsPage = productService.findProductsByName("Test", pageable);

        assertEquals(page, productsPage);
        assertEquals(page.getContent().size(), productsPage.getContent().size());
        assertEquals(page.getContent().get(0), productsPage.getContent().get(0));
    }

    @Test
    void shouldFindAllByCategory() throws NullPointerException {
        List<Product> products = new ArrayList<>();
        Product product = prepareProduct();
        product.setCategory(prepareCategory());
        products.add(product);
        products.add(product);
        products.add(product);
        products.add(product);
        Pageable pageable = PageRequest.of(1, 1);
        Page page = fillPage(products, pageable);

        when(productRepository.findAllByCategory(product.getCategory())).thenReturn(products);

        Page<Product> productsPage = productService.findProductsByCategory(product.getCategory(), pageable);

        assertEquals(page, productsPage);
        assertEquals(page.getContent().size(), productsPage.getContent().size());
        assertEquals(page.getContent().get(0), productsPage.getContent().get(0));
    }

    @Test
    void shouldNotFindAllByCategory() throws NullPointerException {
        List<Product> products = new ArrayList<>();
        Product product = prepareProduct();
        product.setCategory(prepareCategory());
        products.add(product);
        products.add(product);
        products.add(product);
        products.add(product);
        Pageable pageable = PageRequest.of(1, 1);
        Page page = fillPage(products, pageable);
        List<Product> empty = new ArrayList<>();

        when(productRepository.findAllByCategory(product.getCategory())).thenReturn(empty);
        when(productRepository.findAll()).thenReturn(products);

        Page<Product> productsPage = productService.findProductsByCategory(product.getCategory(), pageable);

        assertEquals(page, productsPage);
        assertEquals(page.getContent().size(), productsPage.getContent().size());
        assertEquals(page.getContent().get(0), productsPage.getContent().get(0));
    }

    private Page<Product> fillPage(List<Product> productList, Pageable pageable) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int startItem = page * size;
        List<Product> products;
        if (productList.size() < startItem) {
            products = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + size, productList.size());
            products = productList.subList(startItem, toIndex);
        }
        return new PageImpl<>(products,
                PageRequest.of(page, size),
                productList.size());
    }

    private Product prepareProduct() {
        Random random = new Random();
        String[] names = new String[]{"Rower", "Rolki", "Myszka",
                "Garnek", "Kawa", "Woda", "Sol", "Lampa", "Posciel"};
        Product product = new Product();
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
        Producer producer = new Producer();
        Random random = new Random();
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
        Category category = new Category();
        Random random = new Random();
        String[] categories = new String[]{"AGD", "RTV", "Sport", "Gaming",
                "Kuchnia"};
        category.setId(random.nextLong() + 100);
        category.setCategory(categories[random.nextInt(5)]);
        return category;
    }
}