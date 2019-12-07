package pl.filip.tosql.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.filip.tosql.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
