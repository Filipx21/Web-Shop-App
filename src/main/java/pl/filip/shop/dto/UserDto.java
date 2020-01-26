package pl.filip.shop.dto;

import pl.filip.shop.constraint.FieldMatch;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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

    @Size(min = 5, message = "Hasło nie może mieć mniej niż 5 znaków")
    private String password;

    @Size(min = 5, message = "Hasło nie może mieć mniej niż 5 znaków")
    private String confirmPassword;

    @Size(min = 5, message = "Nazwa użytkownika nie może mieć mniej niż 5 znaków")
    private String userName;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
