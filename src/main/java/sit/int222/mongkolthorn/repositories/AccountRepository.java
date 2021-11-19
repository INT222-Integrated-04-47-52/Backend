package sit.int222.mongkolthorn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int222.mongkolthorn.models.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query(value = "SELECT max(accountId) FROM Account")
    Long getMaxAccountId();

    @Query(value = "SELECT a FROM Account a WHERE a.email = ?1")
    Account findByEmail(String email);
}
