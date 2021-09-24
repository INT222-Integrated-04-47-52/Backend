package sit.int222.mongkolthorn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int222.mongkolthorn.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT p FROM Product p WHERE p.name = ?1")
    Product findByName(String pname);

    @Query(value = "SELECT max(productId) FROM Product")
    Long getMaxProductId();
}
