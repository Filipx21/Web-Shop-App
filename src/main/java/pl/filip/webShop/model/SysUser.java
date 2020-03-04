package pl.filip.webShop.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class SysUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String address;
    private String postCode;
    private String city;
    private String email;
    private String password;
    private boolean inUse;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public SysUser() { }

    public SysUser(String firstName, String lastName, String address, String postCode, String city, boolean inUse) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postCode = postCode;
        this.city = city;
        this.inUse = inUse;
    }

    public SysUser(String firstName, String lastName, String address,
                   String postCode, String city,
                   String email, String password, boolean inUse) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postCode = postCode;
        this.city = city;
        this.email = email;
        this.password = password;
        this.inUse = inUse;
    }

    public SysUser(String firstName, String lastName,
                   String address, String postCode,
                   String city, String email, String password,
                   Collection<Role> roles, boolean inUse) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postCode = postCode;
        this.city = city;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.inUse = inUse;
    }

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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SysUser sysUser = (SysUser) obj;
        return Objects.equals(id, sysUser.id)
                && Objects.equals(firstName, sysUser.firstName)
                && Objects.equals(lastName, sysUser.lastName)
                && Objects.equals(address, sysUser.address)
                && Objects.equals(postCode, sysUser.postCode)
                && Objects.equals(city, sysUser.city)
                && Objects.equals(email, sysUser.email)
                && Objects.equals(password, sysUser.password)
                && Objects.equals(roles, sysUser.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, address, postCode, city, email, password, roles);
    }

    @Override
    public String toString() {
        return "SysUser{"
                + "id=" + id
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", address='" + address + '\''
                + ", postCode='" + postCode + '\''
                + ", city='" + city + '\''
                + ", email='" + email + '\''
                + ", password='" + password + '\''
                + ", roles=" + roles
                + '}';
    }
}
