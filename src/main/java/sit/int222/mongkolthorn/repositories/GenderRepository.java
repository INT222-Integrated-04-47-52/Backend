package sit.int222.mongkolthorn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int222.mongkolthorn.models.Gender;

public interface GenderRepository extends JpaRepository<Gender, Long> {
}
