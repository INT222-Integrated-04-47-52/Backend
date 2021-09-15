package sit.int222.mongkolthorn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int222.mongkolthorn.models.Kind;

public interface KindRepository extends JpaRepository<Kind, Long> {
}
