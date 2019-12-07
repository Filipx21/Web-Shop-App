package pl.filip.tosql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.filip.tosql.model.ProductInOrder;

public interface ProductInOrderRepository extends JpaRepository<ProductInOrder, Long> {

}
