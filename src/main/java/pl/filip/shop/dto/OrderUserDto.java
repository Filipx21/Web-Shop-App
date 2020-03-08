package pl.filip.shop.dto;

import pl.filip.shop.model.ProductInOrder;
import pl.filip.shop.model.SysUser;

import java.util.List;

public class OrderUserDto {

    private Long id;
    private List<ProductInOrder> productInOrders;
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
}
