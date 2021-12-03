package sit.int222.mongkolthorn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int222.mongkolthorn.models.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT p FROM Product p WHERE p.name = ?1")
    Product findByName(String pname);

    @Query(value = "SELECT max(productId) FROM Product")
    Long getMaxProductId();

    @Query(value = "SELECT p FROM Product p WHERE p.name LIKE %?1%" +
            " OR p.kind.kindName LIKE %?1%" + " OR p.gender.genderName LIKE %?1%" +
            " OR p.type.typeName LIKE %?1%")
    List<Product> findAllByKeyword(String keyword);

    @Query(value = "SELECT p FROM Product p WHERE p.kind.kindName = ?1")
    List<Product> findAllByProductKind(String kind);

    @Query(value = "SELECT p FROM Product p WHERE p.gender.genderName = ?1")
    List<Product> findAllByProductGender(String gender);

    @Query(value = "SELECT p FROM Product p WHERE p.type.typeName = ?1")
    List<Product> findAllByProductType(String type);

}
