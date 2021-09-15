package sit.int222.mongkolthorn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int222.mongkolthorn.models.Closet;

public interface ClosetRepository extends JpaRepository<Closet, Long> {
}
