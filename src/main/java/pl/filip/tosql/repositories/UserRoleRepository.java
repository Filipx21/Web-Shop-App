package pl.filip.tosql.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.filip.tosql.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByRole(String role);
}
