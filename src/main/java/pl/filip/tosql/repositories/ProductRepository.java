package pl.filip.tosql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.filip.tosql.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
