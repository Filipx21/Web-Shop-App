package pl.filip.shop.resource;

import pl.filip.shop.model.Cart;
import pl.filip.shop.model.OrderUser;
import pl.filip.shop.model.ProductInOrder;
import pl.filip.shop.model.SysUser;
import pl.filip.shop.model.Category;
import pl.filip.shop.model.Producer;
import pl.filip.shop.model.Role;
import pl.filip.shop.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DataTest {

    public Cart prepareCart() {
        Random random = new Random();
        Cart cart = new Cart();
        cart.setId(Math.abs(random.nextLong() + 100));
        cart.setProducts(prepareProductInOrder());
        cart.setSysUser(prepareUser());
        cart.setInUse(false);
        return cart;
    }

    public OrderUser prepareOrderUser() {
        Random random = new Random();
        OrderUser orderUser = new OrderUser();
        orderUser.setId(Math.abs(random.nextLong() + 100));
        orderUser.setProductInOrders(prepareProductInOrder());
        orderUser.setSysUser(prepareUser());
        orderUser.setDone(true);
        orderUser.setFinish(false);
        return orderUser;
    }

    public List<ProductInOrder> prepareProductInOrder() {
        List<Product> allProducts = List.of(
                prepareProduct(),
                prepareProduct(),
                prepareProduct(),
                prepareProduct(),
                prepareProduct(),
                prepareProduct(),
                prepareProduct(),
                prepareProduct()
        );
        Random random = new Random();
        return allProducts
                .stream()
                .map(ProductInOrder::new)
                .filter(x -> {
                    x.setId(Math.abs(random.nextLong() + 100));
                    return true;
                })
                .collect(Collectors.toList());
    }

    public Product prepareProduct() {
        Random random = new Random();
        String[] names = new String[]{"Rower", "Rolki", "Myszka",
                "Garnek", "Kawa", "Woda", "Sol", "Lampa", "Posciel"};
        Product product = new Product();
        product.setId(Math.abs(random.nextLong() + 100));
        product.setProductName(names[random.nextInt(8)]);
        product.setDescript(names[random.nextInt(8)]);
        product.setQuantity(random.nextInt(100) + 1);
        product.setCost(new BigDecimal(random.nextDouble() * 100 + 1));
        product.setProducer(preapreProducer());
        product.setCreatedDate(LocalDate.now());
        product.setCategory(prepareCategory());
        return product;
    }

    public SysUser prepareUser() {
        SysUser user = new SysUser();
        Random random = new Random();
        String[] lastNames = new String[] {"Kwadrat","Giwera","Maven","Konik","Ura"};
        String[] firstNames = new String[] {"Eustachy","Gienia","Ewelina","Kornel","Bartek"};
        String[] addresses = new String[] {"Kornicza 2","Ernesta 33","Mavena 3","Konika 66","Orki 99"};
        String[] cities = new String[] {"Wroclaw","Warszawa","Gdansk","Gdynia","Zakopane"};
        String[] emails = new String[] {"test1@gmail.com","test2@gmail.com","test3@gmail.com","test4@gmail.com","test5@gmail.com"};
        Collection roles = new ArrayList();
        roles.add(prepareRole());
        user.setId(Long.valueOf(random.nextInt(1000)));
        user.setFirstName(firstNames[random.nextInt(5)]);
        user.setLastName(lastNames[random.nextInt(5)]);
        user.setAddress(addresses[random.nextInt(5)]);
        user.setPostCode("22-321");
        user.setCity(cities[random.nextInt(5)]);
        user.setEmail(emails[random.nextInt(5)]);
        user.setPassword("123");
        user.setInUse(true);
        user.setRoles(roles);
        return user;
    }

    public Producer preapreProducer() {
        Producer producer = new Producer();
        Random random = new Random();
        String[] names = new String[]{"PCC", "Wapniaki", "JA", "Mavos",
                "Intermodal", "DCOM", "Oldb", "Michalki", "Test"};
        String[] addresses = new String[]{"Ruska 55", "Kamien 2", "Wroclaw 44",
                "Warszawa 555", "Wilcza 43", "sfdfsd 34", "Testowa 33",
                "Testowa 22", "Testowa 13"};
        producer.setId(random.nextLong() + 100);
        producer.setAddress(addresses[random.nextInt(8)]);
        producer.setProducerName(names[random.nextInt(8)]);
        return producer;
    }

    public Category prepareCategory() {
        Category category = new Category();
        Random random = new Random();
        String[] categories = new String[]{"AGD", "RTV", "Sport", "Gaming",
                "Kuchnia"};
        category.setCategory(categories[random.nextInt(5)]);
        category.setId(random.nextLong() + 100);
        return category;
    }



    private Role prepareRole() {
        String[] roles = new String[] {"ADMIN", "USER"};
        Role role = new Role();
        Random random = new Random();
        role.setId(Long.valueOf(random.nextInt(1000)));
        role.setName(roles[random.nextInt(2)]);
        return role;
    }
}
