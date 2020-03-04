package pl.filip.webShop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.filip.webShop.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
