package sit.int222.mongkolthorn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int222.mongkolthorn.models.Size;

public interface SizeRepository extends JpaRepository <Size, Long> {
}
