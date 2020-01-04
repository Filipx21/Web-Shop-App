package pl.filip.tosql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.filip.tosql.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
