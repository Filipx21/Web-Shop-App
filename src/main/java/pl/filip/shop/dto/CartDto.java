package pl.filip.shop.dto;

import pl.filip.shop.model.ProductInOrder;
import pl.filip.shop.model.SysUser;

import java.util.List;

public class CartDto {

    private Long id;
    private List<ProductInOrder> products;
    private SysUser sysUser;
    private boolean inUse;

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
