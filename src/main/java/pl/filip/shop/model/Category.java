package pl.filip.shop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Column;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String category;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Product> product;

    public Category() {
    }

    public Category(String category) {
        this.category = category;
    }

    public Category(String category, Collection<Product> product) {
        this.category = category;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Collection<Product> getProduct() {
        return product;
    }

    public void setProduct(Collection<Product> product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Category category1 = (Category) obj;
        return Objects.equals(id, category1.id)
                && Objects.equals(category, category1.category)
                && Objects.equals(product, category1.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, product);
    }

    @Override
    public String toString() {
        return "Category{"
                + "id=" + id
                + ", category='" + category + '\''
                + ", product=" + product
                + '}';
    }
}
