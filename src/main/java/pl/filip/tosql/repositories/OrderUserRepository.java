package pl.filip.tosql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.filip.tosql.model.OrderUser;
import pl.filip.tosql.model.User;

import java.util.List;

public interface OrderUserRepository extends JpaRepository<OrderUser, Long> {

    List<OrderUser> findAllByUser(User user);
}
