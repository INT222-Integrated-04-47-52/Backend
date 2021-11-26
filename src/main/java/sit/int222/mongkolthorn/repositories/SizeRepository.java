package sit.int222.mongkolthorn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int222.mongkolthorn.models.Size;

public interface SizeRepository extends JpaRepository <Size, Long> {
    @Query(value = "SELECT max(sizeId) FROM Size")
    Long getMaxSizeId();
}
