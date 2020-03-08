package pl.filip.shop.dto;

import pl.filip.shop.model.Product;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;

public class CategoryDto {

    private Long id;

    @NotEmpty(message = "Kategoria nie moze byc pusta")
    private String category;

    private Collection<Product> product;

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
}
