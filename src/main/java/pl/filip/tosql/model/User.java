package pl.filip.tosql.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(5)
    @Column(unique = true, nullable = false, length = 30)
    private String username;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    @Size(min = 5, message = "Password is too short")
    private String password;
    @NotEmpty
    @Size(min = 2, message = "Name is too short")
    private String name;
    @NotEmpty
    private String phone;
    @NotEmpty
    private String address;
    private boolean active;
    @Column(nullable = false)
    private UserRole role;

    public User(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.active = user.isActive();
        this.role = user.getRole();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return active == user.active &&
            Objects.equals(id, user.id) &&
            Objects.equals(username, user.username) &&
            Objects.equals(email, user.email) &&
            Objects.equals(password, user.password) &&
            Objects.equals(name, user.name) &&
            Objects.equals(phone, user.phone) &&
            Objects.equals(address, user.address) &&
            Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, name, phone, address, active, role);
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", name='" + name + '\'' +
            ", phone='" + phone + '\'' +
            ", address='" + address + '\'' +
            ", active=" + active +
            ", role=" + role +
            '}';
    }
}

