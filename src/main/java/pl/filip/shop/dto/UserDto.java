package pl.filip.shop.dto;

import pl.filip.shop.constraint.FieldMatch;

import javax.validation.constraints.*;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
        @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
})
public class UserDto {

    private  Long id;

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

    @Size(min = 5, message = "Hasło nie może mieć mniej niż 5 znaków")
    private String password;

    @Size(min = 5, message = "Hasło nie może mieć mniej niż 5 znaków")
    private String confirmPassword;

    @Email(message = "E-mail musi zawierać @")
    @NotEmpty(message = "E-mail nie może być pusty")
    private String email;

    @Email(message = "E-mail musi zawierać @")
    @NotEmpty(message = "E-mail nie może być pusty")
    private String confirmEmail;

    @AssertTrue(message = "Zaakceptuj warunki")
    private Boolean terms;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public Boolean getTerms() {
        return terms;
    }

    public void setTerms(Boolean terms) {
        this.terms = terms;
    }
}
