package pl.filip.shop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Product {

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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Producer producer;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private Category category;

    private LocalDate createdDate;

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

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product product = (Product) obj;
        return quantity == product.quantity
                && Objects.equals(id, product.id)
                && Objects.equals(productName, product.productName)
                && Objects.equals(descript, product.descript)
                && Objects.equals(cost, product.cost)
                && Objects.equals(producer, product.producer)
                && Objects.equals(category, product.category)
                && Objects.equals(createdDate, product.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, descript, quantity, cost, producer, category, createdDate);
    }

    @Override
    public String toString() {
        return "Product{"
                + "id=" + id
                + ", productName='" + productName + '\''
                + ", descript='" + descript + '\''
                + ", quantity=" + quantity
                + ", cost=" + cost
                + ", producer=" + producer
                + ", category=" + category
                + ", createdDate=" + createdDate
                + '}';
    }
}
