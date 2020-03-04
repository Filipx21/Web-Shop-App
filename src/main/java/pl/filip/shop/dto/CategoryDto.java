package pl.filip.shop.dto;

import javax.validation.constraints.NotEmpty;

public class CategoryDto {

    @NotEmpty(message = "Kategoria nie moze byc pusta")
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
