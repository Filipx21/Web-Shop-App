package pl.filip.tosql.model;

import java.math.BigDecimal;
import java.util.List;

public class Order {

    private Long id;
    private List<Product> products;
    private Customer customer;
    private BigDecimal price;

}
