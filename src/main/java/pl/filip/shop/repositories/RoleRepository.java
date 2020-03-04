package pl.filip.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.filip.shop.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
