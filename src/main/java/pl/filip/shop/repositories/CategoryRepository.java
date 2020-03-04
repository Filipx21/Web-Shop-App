package pl.filip.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.filip.shop.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
