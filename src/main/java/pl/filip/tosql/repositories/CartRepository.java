package pl.filip.tosql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.filip.tosql.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
