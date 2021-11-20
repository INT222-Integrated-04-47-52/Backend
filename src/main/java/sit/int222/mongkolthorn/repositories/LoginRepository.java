package sit.int222.mongkolthorn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int222.mongkolthorn.models.Login;

public interface LoginRepository extends JpaRepository<Login,Long> {
    @Query(value = "SELECT max(loginId) FROM Login")
    Long getMaxLoginId();

    @Query(value = "SELECT log FROM Login log WHERE log.logEmail = ?1")
    Login findByEmail(String email);

    @Query(value = "SELECT log FROM  Login log WHERE log.logPassword = ?1")
    Login findByPassword(String password);
}
