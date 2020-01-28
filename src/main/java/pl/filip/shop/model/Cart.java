package pl.filip.shop.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductInOrder> products;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    private boolean inUse;

    public Cart() {
    }

    public Cart(User user, boolean inUse) {
        this.user = user;
        this.inUse = inUse;
    }

    public Cart(List<ProductInOrder> products, User user, boolean inUse) {
        this.products = products;
        this.user = user;
        this.inUse = inUse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductInOrder> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInOrder> products) {
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }
}
