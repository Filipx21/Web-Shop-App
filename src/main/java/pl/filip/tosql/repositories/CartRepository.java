package pl.filip.tosql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.filip.tosql.model.Cart;
import pl.filip.tosql.model.User;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUserAndInUse(User user, boolean inUse);

    Cart findByUserAndInUse(User user, boolean inUse);
}
