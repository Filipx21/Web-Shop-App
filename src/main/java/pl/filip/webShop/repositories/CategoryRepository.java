package pl.filip.webShop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.filip.webShop.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
