package pl.filip.webShop.model;

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
    private SysUser sysUser;

    private boolean inUse;

    public Cart() {
    }

    public Cart(SysUser sysUser, boolean inUse) {
        this.sysUser = sysUser;
        this.inUse = inUse;
    }

    public Cart(List<ProductInOrder> products, SysUser sysUser, boolean inUse) {
        this.products = products;
        this.sysUser = sysUser;
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

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }
}
