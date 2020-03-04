package pl.filip.shop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.util.List;
import java.util.Objects;

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

    public Cart(List<ProductInOrder> products,
                SysUser sysUser, boolean inUse) {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Cart cart = (Cart) obj;
        return inUse == cart.inUse
                && Objects.equals(id, cart.id)
                && Objects.equals(products, cart.products)
                && Objects.equals(sysUser, cart.sysUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, products, sysUser, inUse);
    }

    @Override
    public String toString() {
        return "Cart{"
                + "id=" + id
                + ", products=" + products
                + ", sysUser=" + sysUser
                + ", inUse=" + inUse
                + '}';
    }
}
