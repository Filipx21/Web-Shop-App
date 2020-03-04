package pl.filip.webShop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;

import java.time.LocalDate;

@Entity
public class ProductInOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String productName;

    @NotEmpty
    @Size(max = 10000)
    private String descript;

    @NotNull
    @Min(0)
    private int quantity;

    @NotNull
    @Min(0)
    private BigDecimal cost;

    @NotEmpty
    private String producerName;

    @NotEmpty
    private String address;

    private LocalDate createdDate;

    public ProductInOrder() {
    }

    public ProductInOrder(Product product) {
        this.productName = product.getProductName();
        this.descript = product.getDescript();
        this.quantity = product.getQuantity();
        this.cost = product.getCost();
        this.producerName = product.getProducer().getProducerName();
        this.address = product.getProducer().getAddress();
        this.createdDate = product.getCreatedDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "ProductInOrder{"
                + "id=" + id
                + ", productName='" + productName + '\''
                + ", descript='" + descript + '\''
                + ", quantity=" + quantity
                + ", cost=" + cost
                + ", producerName='" + producerName + '\''
                + ", address='" + address + '\''
                + ", createdDate=" + createdDate
                + '}';
    }
}
