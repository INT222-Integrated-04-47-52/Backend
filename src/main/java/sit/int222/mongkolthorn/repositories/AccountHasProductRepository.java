package sit.int222.mongkolthorn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int222.mongkolthorn.models.AccountHasProduct;

public interface AccountHasProductRepository extends JpaRepository<AccountHasProduct, Long> {
}
