package pl.filip.shop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import java.util.List;
import java.util.Objects;

@Entity
public class OrderUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductInOrder> productInOrders;

    @OneToOne(fetch = FetchType.LAZY)
    private SysUser sysUser;

    private boolean done;

    private boolean finish;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductInOrder> getProductInOrders() {
        return productInOrders;
    }

    public void setProductInOrders(List<ProductInOrder> productInOrders) {
        this.productInOrders = productInOrders;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        OrderUser orderUser = (OrderUser) obj;
        return done == orderUser.done
                && finish == orderUser.finish
                && Objects.equals(id, orderUser.id)
                && Objects.equals(productInOrders, orderUser.productInOrders)
                && Objects.equals(sysUser, orderUser.sysUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productInOrders, sysUser, done, finish);
    }

    @Override
    public String toString() {
        return "OrderUser{"
                + "id=" + id
                + ", productInOrders=" + productInOrders
                + ", sysUser=" + sysUser
                + ", done=" + done
                + ", finish=" + finish
                + '}';
    }
}
