package sit.int222.mongkolthorn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int222.mongkolthorn.models.Account;
import sit.int222.mongkolthorn.models.Closet;

import java.util.List;

public interface ClosetRepository extends JpaRepository<Closet, Long> {
    @Query(value = "SELECT max(closetId) FROM Closet")
    Long getMaxClosetId();

    List<Closet> findAllByAccount(Account account);
}
