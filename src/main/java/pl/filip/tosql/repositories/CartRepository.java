package pl.filip.tosql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.filip.tosql.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
