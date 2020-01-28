package pl.filip.shop.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class EditUserDto {

    private Long id;

    @NotEmpty(message = "Podaj imię")
    private String firstName;

    @NotEmpty(message = "Podaj nazwisko")
    private String lastName;

    @NotEmpty(message = "Podaj ulice i numer budynku")
    private String address;

    @Pattern(regexp = "[0-9]{2}-[0-9]{3}", message = "Niepoprawny kod pocztowy. xx-xxx")
    private String postCode;

    @NotEmpty(message = "Miejscowość nie moze byc pusta")
    private String city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
