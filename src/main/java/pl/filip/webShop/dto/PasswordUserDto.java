package pl.filip.webShop.dto;

import pl.filip.webShop.constraint.FieldMatch;

import javax.validation.constraints.Size;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "Hasła nie sa takie same"),
})
public class PasswordUserDto {

    @Size(min = 5, message = "Hasło nie może mieć mniej niż 5 znaków")
    private String password;

    @Size(min = 5, message = "Hasło nie może mieć mniej niż 5 znaków")
    private String confirmPassword;

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
}
