package sit.int222.mongkolthorn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int222.mongkolthorn.models.ProductHasColors;

public interface ProductHasColorsRepository extends JpaRepository<ProductHasColors, Long> {
    @Query(value = "SELECT max(hasColorsId) FROM ProductHasColors")
    Long getMaxProductHasColorsId();
}
