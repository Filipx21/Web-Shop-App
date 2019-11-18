package pl.filip.tosql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.filip.tosql.model.Producer;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {
}
