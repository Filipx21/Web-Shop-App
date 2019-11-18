package pl.filip.tosql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.filip.tosql.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
