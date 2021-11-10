package sit.int222.mongkolthorn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int222.mongkolthorn.models.Login;

public interface LoginRepository extends JpaRepository<Login,Long> {
}
