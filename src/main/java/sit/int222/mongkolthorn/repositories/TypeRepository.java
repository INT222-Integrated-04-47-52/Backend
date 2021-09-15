package sit.int222.mongkolthorn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int222.mongkolthorn.models.Type;

public interface TypeRepository extends JpaRepository<Type, Long> {
}
