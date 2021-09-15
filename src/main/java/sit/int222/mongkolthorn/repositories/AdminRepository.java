package sit.int222.mongkolthorn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int222.mongkolthorn.models.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
