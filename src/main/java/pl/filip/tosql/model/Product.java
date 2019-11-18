package pl.filip.tosql.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Size(max = 500)
    private String descript;

    @NotNull
    @Min(0)
    private Integer quantity;

    @NotNull
    @Min(0)
    private BigDecimal cost;

    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductType type;

    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    private Producer producer;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime create;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime update;

    @OneToOne
    private ProductInOrder productInOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public LocalDateTime getCreate() {
        return create;
    }

    public void setCreate(LocalDateTime create) {
        this.create = create;
    }

    public LocalDateTime getUpdate() {
        return update;
    }

    public void setUpdate(LocalDateTime update) {
        this.update = update;
    }

    public ProductInOrder getProductInOrder() {
        return productInOrder;
    }

    public void setProductInOrder(ProductInOrder productInOrder) {
        this.productInOrder = productInOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(descript, product.descript) &&
                Objects.equals(quantity, product.quantity) &&
                Objects.equals(cost, product.cost) &&
                Objects.equals(type, product.type) &&
                Objects.equals(producer, product.producer) &&
                Objects.equals(create, product.create) &&
                Objects.equals(update, product.update) &&
                Objects.equals(productInOrder, product.productInOrder);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, descript, quantity, cost, type, producer, create, update, productInOrder);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", descript='" + descript + '\'' +
                ", quantity=" + quantity +
                ", cost=" + cost +
                ", type=" + type +
                ", producer=" + producer +
                ", create=" + create +
                ", update=" + update +
                ", productInOrder=" + productInOrder +
                '}';
    }
}
